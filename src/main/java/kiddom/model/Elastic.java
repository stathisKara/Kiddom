package kiddom.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.*;


import java.net.InetAddress;
import java.util.*;

/**
 * Created by Christos on 30/6/2017.
 */
public class Elastic {


    public RestClient getRestClient(){
        RestClient restClient = RestClient.builder( new HttpHost("localhost", 9200, "http")).build();

        return restClient;
    }

    public void create(RestClient restClient, String id, String name, String date, String descr, String cat, String subcat1, String subcat2, String subcat3
            , String town, String area, String address, Integer number, String postcode, float lat, float lon){

        HttpEntity entity = null;
        try {
            entity = new NStringEntity("{"+
                    "\"name\" : \"" + name +"\"," +
                    "\"date\" : \""+date+"\"," +
                    "\"description\" : \""+descr+"\"," +
                    "\"category\" : \""+cat+"\"," +
                    "\"subCat1\" : \""+subcat1+"\"," +
                    "\"subCat2\" : \""+subcat2+"\"," +
                    "\"subCat3\" : \""+subcat3+"\"," +
                    "\"town\" : \""+town+"\"," +
                    "\"area\" : \""+area+"\"," +
                    "\"address\" : \""+address+"\"," +
                    "\"number\" : \""+number+"\"," +
                    "\"postcode\" : \""+postcode+"\","+
                    "\"geolocation\" : {"+
                        "\"lat\" : \""+Float.toString(lat)+"\","+
                        "\"lon\" : \""+Float.toString(lon)+"\""+
                            "}"+
                    "}", ContentType.APPLICATION_JSON);

            restClient.performRequest(
                    "PUT",
                    "/index/event/"+id,
                    Collections.<String, String>emptyMap(),
                    entity);
        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, String> getEntry(Client client, String id){
        GetResponse response;
        Map<Integer, HashMap<String, String>> result = new HashMap<Integer, HashMap<String, String>>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = client.prepareGet("index", "event", id).get();
            result.put(0, mapper.readValue(response.getSourceAsString(), new TypeReference<HashMap<String, String>>(){}));
        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }

        return result.get(0);
    }

    public void deleteIndex(RestClient restClient){
        try{
            Response response = restClient.performRequest("DELETE", "/index", Collections.singletonMap("pretty", "true"));
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(Client restClient, String id){
        try{
            DeleteResponse response = restClient.prepareDelete("index", "event", id).get();
        }
        catch (Exception e){
            System.out.println("Exception at restClient.prepareDelete()");
            System.out.println(e.getMessage());
        }
    }

    public void update(RestClient restClient, String id,String name, String date, String descr, String cat, String subcat1, String subcat2, String subcat3
            , String town, String area, String address, String number, String start, String end, String avail, String price, String postcode){

//        try {
//            UpdateRequest updateRequest = new UpdateRequest();
//            updateRequest.index("index");
//            updateRequest.type("event");
//            updateRequest.id(id);
//            updateRequest.doc(jsonBuilder()
//                    .startObject()
//                    .field("name", name)
//                    .field("date", date)
//                    .field("description", descr)
//                    .field("category", cat)
//                    .field("subCat1", subcat1)
//                    .field("subCat2", subcat2)
//                    .field("subCat3", subcat3)
//                    .field("town", town)
//                    .field("area", area)
//                    .field("address", address)
//                    .field("number", number)
//                    .field("start_time", start)
//                    .field("end_time", end)
//                    .field("availability", avail)
//                    .field("price", price)
//                    .field("postcode", postcode)
//                    .endObject());
//            restClient.update(updateRequest).get();
//        }
//        catch (Exception e){
//            System.out.println("Exception at update");
//            System.out.println(e.getMessage());
//        }
    }

    public String getResponse(Response response){

        String s="";
        try{

            s = EntityUtils.toString(response.getEntity());
            //System.out.println(EntityUtils.toString(response.getEntity()));
            System.out.println(s);
        }
        catch (Exception e){
            System.out.println("EntityUtils.toString(response.getEntity()");
            System.out.println(e.getMessage());
        }
        return s;
    }

    public List<Integer> search(RestClient restClient, String query){

        List<Integer> results = new ArrayList<Integer>();
        try {

            HttpEntity entity2=new NStringEntity("{\"query\": {"+
                    "\"multi_match\": {"+
                    "\"query\": \""+query+"\","+
                    "\"analyzer\": \"search\","+
                    "\"fields\": ["+
                    "\"name\","+
                    "\"description\","+
                    "\"category\","+
                    "\"subCat1\","+
                    "\"subCat2\","+
                    "\"subCat3\""+
                    "],"+
                    "\"fuzziness\" : \"AUTO\","+
                    "\"prefix_length\" : 2"+
                    "}"+"}}", ContentType.APPLICATION_JSON);

            Response response = restClient.performRequest(
                    "GET",
                    "/index/_search",
                    Collections.<String, String>emptyMap(),
                    entity2);

            String s = getResponse(response);

            JSONObject obj = new JSONObject(s);
            JSONObject obj2=obj.getJSONObject("hits");

            JSONArray array = obj2.getJSONArray("hits");
            for(int i = 0 ; i < array.length() ; i++){
                results.add(Integer.parseInt(array.getJSONObject(i).getString("_id")));
            }
            System.out.println(results);
        }
        catch (Exception e) {
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }
       return results;
    }



    public List<Integer> searchRange(RestClient restClient, float lat, float lon, float dist){

        List<Integer> results = new ArrayList<Integer>();
        try {

            HttpEntity entity2=new NStringEntity("{"+
                    "\"query\": {"+
                    "\"bool\" : {"+
                    "\"must\" : {"+
                        "\"match_all\" : {}"+
                    "},"+
                    "\"filter\" : {"+
                        "\"geo_distance\" : {"+
                            "\"distance\" : \""+dist+"km\","+
                                "\"location\" : {"+
                                    "\"lat\" : "+lat+","+
                                    "\"lon\" :"+lon+
                            "} } } } }}", ContentType.APPLICATION_JSON);

            Response response = restClient.performRequest(
                    "GET",
                    "/index/_search",
                    Collections.<String, String>emptyMap(),
                    entity2);

            String s = getResponse(response);

            JSONObject obj = new JSONObject(s);

            //List<String> list = new ArrayList<String>();
            JSONObject obj2=obj.getJSONObject("hits");
            JSONArray array = obj2.getJSONArray("hits");
            for(int i = 0 ; i < array.length() ; i++){
                results.add(Integer.parseInt(array.getJSONObject(i).getString("_id")));
            }
            System.out.println(results);

        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }
        System.out.println("epistrefw"+results);
        return results;
    }




    public void closeClient(RestClient restClient){
        try {
            restClient.close();
        }
        catch (Exception e){
            System.out.println("Exception at restClient.close()");
            System.out.println(e.getMessage());
        }
    }

    public void closeClient(Client restClient){
        try {
            restClient.close();
        }
        catch (Exception e){
            System.out.println("Exception at restClient.close()");
            System.out.println(e.getMessage());
        }
    }

    public void indexer(RestClient restClient){
        HttpEntity index = new NStringEntity(
                "{" +
                        "\"settings\": {"+
                        "\"number_of_shards\": 1,"+
                        "\"number_of_replicas\": 0,"+
                        "\"analysis\": {"+
                        "\"filter\": {"+
                        "\"greek_stop\": {"+
                        "\"type\": \"stop\","+
                        "\"stopwords\": \"_greek_\""+
                        "},"+
                        "\"greek_lowercase\": {"+
                        "\"type\": \"lowercase\","+
                        "\"language\": \"greek\""+
                        "},"+
                        "\"greek_keywords\": {"+
                        "\"type\": \"keyword_marker\","+
                        "\"keywords\": ["+
                        "\"παράδειγμα\""+
                        "]"+
                        "},"+
                        "\"greek_stemmer\": {"+
                        "\"type\": \"stemmer\","+
                        "\"language\": \"greek\""+
                        "},"+
                        "\"ngram_filter\": {"+
                        "\"type\": \"nGram\","+
                        "\"min_gram\": 3,"+
                        "\"max_gram\": 4"+
                        "}"+
                        "},"+
                        "\"analyzer\": {"+
                        "\"greek\": {"+
                        "\"type\": \"custom\","+
                        "\"tokenizer\": \"icu_tokenizer\","+
                        "\"filter\": ["+
                        "\"greek_lowercase\","+
                        "\"ngram_filter\","+
                        "\"greek_stop\","+
                        "\"greek_keywords\","+
                        "\"greek_stemmer\","+
                        "\"icu_folding\""+
                        "]"+
                        "},"+
                        "\"search\" : {"+
                        "\"type\": \"custom\","+
                        "\"tokenizer\": \"icu_tokenizer\","+
                        "\"filter\": ["+
                        "\"greek_lowercase\","+
                        "\"greek_stop\","+
                        "\"greek_keywords\","+
                        "\"greek_stemmer\","+
                        "\"icu_folding\""+
                        "]"+
                        "}"+
                        "}"+
                        "}"+
                        "},"+
                        "\"mappings\": {"+
                        "\"event\": {"+
                        "\"properties\": {"+
                        "\"name\": {"+
                        "\"type\": \"text\","+
                        "\"term_vector\": \"yes\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"date\": {"+
                        "\"type\": \"date\","+
                        "\"format\": \"dd-MM-yyyy\""+
                        "},"+
                        "\"description\": {"+
                        "\"type\": \"text\","+
                        "\"term_vector\": \"yes\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"category\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"subCat1\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"subCat2\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"subCat3\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"town\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"area\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"address\": {"+
                        "\"type\": \"text\","+
                        "\"analyzer\": \"greek\""+
                        "},"+
                        "\"number\": {"+
                        "\"type\": \"text\""+
                        "},"+
                        "\"postcode\": {"+
                        "\"type\": \"text\""+
                        "},"+
                        "\"start_time\": {"+
                        "\"type\": \"text\""+
                        "},"+
                        "\"end_time\": {"+
                        "\"type\": \"text\""+
                        "},"+
                        "\"availability\": {"+
                        "\"type\": \"text\""+
                        "},"+
                        "\"price\": {"+
                        "\"type\": \"integer\""+
                        "},"+
                        "\"geolocation\" : {"+
                        "\"type\" : \"geo_point\""+
                        "}"+
                        "}"+
                        "}"+
                        "}"+
                        "}"
                , ContentType.APPLICATION_JSON);

        try{
            Response response = restClient.performRequest(
                    "PUT",
                    "/index",
                    Collections.<String, String>emptyMap(),
                    index);
        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }
    }

    public List<Integer> searchDate(RestClient restClient, String from, String to){

        List<Integer> results = new ArrayList<Integer>();
        try {

            HttpEntity entity2=new NStringEntity("{"+
                    "\"query\": {"+
                    "\"range\" : {"+
                    "\"date\" : {"+
                    "\"gte\": \""+from+"\","+
                    "\"lte\": \""+to+"\""+
                    "} } } }", ContentType.APPLICATION_JSON);

            Response response = restClient.performRequest(
                    "GET",
                    "/index/_search",
                    Collections.<String, String>emptyMap(),
                    entity2);

            String s = getResponse(response);

            JSONObject obj = new JSONObject(s);

            JSONObject obj2=obj.getJSONObject("hits");

            JSONArray array = obj2.getJSONArray("hits");
            for(int i = 0 ; i < array.length() ; i++){
                results.add(Integer.parseInt(array.getJSONObject(i).getString("_id")));
            }

        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }

        return results;
    }

    public List<Integer> searchDateAndQuery(RestClient restClient, String query, String from, String to){

        List<Integer> results = new ArrayList<Integer>();
        try {

            HttpEntity entity2=new NStringEntity("{"+
                    "\"query\": {"+
                    "\"bool\"  : {"+
                    "\"must\" : ["+
                    "{"+
                    "\"multi_match\": {"+
                    "\"query\": \""+query+"\","+
                    "\"analyzer\": \"search\","+
                    "\"fields\": ["+
                    "\"name\","+
                    "\"description\","+
                    "\"category\","+
                    "\"subCat1\","+
                    "\"subCat2\","+
                    "\"subCat3\""+
                    "]"+
                    "}"+
                    "},"+
                    "{"+
                    "\"range\" : {"+
                    "\"date\" : {"+
                    "\"gte\": \""+from+"\","+
                    "\"lte\": \""+to+"\""+
                    "}"+
                    "} } ] } } }", ContentType.APPLICATION_JSON);

            Response response = restClient.performRequest(
                    "GET",
                    "/index/_search",
                    Collections.<String, String>emptyMap(),
                    entity2);

            String s = getResponse(response);

            JSONObject obj = new JSONObject(s);

            List<String> list = new ArrayList<String>();
            JSONObject obj2=obj.getJSONObject("hits");

            JSONArray array = obj2.getJSONArray("hits");
            for(int i = 0 ; i < array.length() ; i++){
                list.add(array.getJSONObject(i).getString("_id"));
            }
            System.out.println(list);

        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }

        return results;
    }


    public List<Integer> searchDateAndRangeAndQuery(RestClient restClient, String query, String from, String to, float lat, float lon, float dist){

        List<Integer> results = new ArrayList<Integer>();
        try {

            HttpEntity entity2=new NStringEntity("{"+
                    "\"query\": {"+
                    "\"bool\"  : {"+
                    "\"must\" : ["+
                    "{"+
                    "\"multi_match\": {"+
                    "\"query\": \""+query+"\","+
                    "\"analyzer\": \"search\","+
                    "\"fields\": ["+
                    "\"name\","+
                    "\"description\","+
                    "\"category\","+
                    "\"subCat1\","+
                    "\"subCat2\","+
                    "\"subCat3\""+
                    "]"+
                    "}"+
                    "},"+
                    "{"+
                    "\"range\" : {"+
                    "\"date\" : {"+
                    "\"gte\": \""+from+"\","+
                    "\"lte\": \""+to+"\""+
                    "}"+
                    "} } ],"+
                    "\"filter\" : {"+
                    "\"geo_distance\" : {"+
                    "\"distance\" : \""+dist+"km\","+
                    "\"geolocation\" : {"+
                    "\"lat\" : "+lat+","+
                    "\"lon\" :"+lon+
                    "} } } } } }", ContentType.APPLICATION_JSON);

            Response response = restClient.performRequest(
                    "GET",
                    "/index/_search",
                    Collections.<String, String>emptyMap(),
                    entity2);

            String s = getResponse(response);

            JSONObject obj = new JSONObject(s);

            JSONObject obj2=obj.getJSONObject("hits");

            JSONArray array = obj2.getJSONArray("hits");
            for(int i = 0 ; i < array.length() ; i++){
                results.add(Integer.parseInt(array.getJSONObject(i).getString("_id")));
            }

        }
        catch (Exception e){
            System.out.println("Exception at restClient.performRequest()");
            System.out.println(e.getMessage());
        }

        return results;
    }

}
