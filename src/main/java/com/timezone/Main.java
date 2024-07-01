package com.timezone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.timezone.Constants.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // filter configs
        JSONObject filterConfig = jsonFileReader(CONFIG_JSON);

        // filterValues
        JSONObject filterValues = jsonFileReader(FILTER_VALUES);

        // report obj
        JSONObject withTZReportObj = jsonFileReader(WITH_TZ_REPORT_GROUP_OBJ);

        JSONObject withoutTZReportObj = jsonFileReader(WITHOUT_TZ_REPORT_GROUP_OBJ);

        // define the flow type
        String flowType = UNRESOLVED_TICKETS;

        Map<String, List<Map<String, Map<String, String>>>> queries = new HashMap<>();


        if (flowType.equals(NORMAL) || flowType.equals(RESOLVED_TICKETS)) {
            for (String key : normalOperators) {

                System.out.println("***************" + key + "********************");

                JSONObject currConfig = filterConfig.getJSONObject(key);
                JSONArray operators = !key.equals(normalOperators[9]) ? currConfig.getJSONArray(KEYS) : null;
                JSONObject filterValue = currConfig.getJSONObject(CONFIG);

                List<Map<String, Map<String, String>>> listOfQueries = new ArrayList<>();

                if (key.equals(normalOperators[0]) || key.equals(normalOperators[6]) || key.equals(normalOperators[7]) || key.equals(normalOperators[8])) {

                    for (int i = 0; i < operators.length(); i++) {

                        filterValue.put(VALUE, operators.getString(i));

                        // WITH_TZ
                        JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                        withTzFiltersMap.put(OPERATOR, key);
                        withTzFiltersMap.put(FILTER_VALUE, filterValue);
                        setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                        // WITHOUT_TZ
                        JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                        withoutTzFiltersMap.put(OPERATOR, key);
                        withoutTzFiltersMap.put(FILTER_VALUE, filterValue);
                        setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                        String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                        String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                        listOfQueries.add(constructFinalMap(getMapWithQueries(withTZ, WithoutTZ), operators.getString(i)));
                    }

                    queries.put(key, listOfQueries);
                }

                if (key.equals(normalOperators[1])) {

                    filterValue.put(VALUE, operators);
                    filterValue.put(TRANSLATED_LABEL, operators);

                    // WITH_TZ
                    JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                    withTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                    // WITHOUT_TZ
                    JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                    withoutTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                    String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                    String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                    listOfQueries.add(constructFinalMap(getMapWithQueries(withTZ, WithoutTZ), TIMESTAMP_ONLY));
                    queries.put(key, listOfQueries);
                }

                if (key.equals(normalOperators[2]) || key.equals(normalOperators[3]) || key.equals(normalOperators[4]) || key.equals(normalOperators[5])) {

                    for (int i = 0; i < operators.length(); i++) {

                        filterValue.put(VALUE, filterValues.getJSONObject(key).getString(operators.getString(i)));
                        filterValue.put(UNIT, operators.getString(i));

                        // WITH_TZ
                        JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                        withTzFiltersMap.put(OPERATOR, key);
                        setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                        // WITHOUT_TZ
                        JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                        withoutTzFiltersMap.put(OPERATOR, key);
                        setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                        String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                        String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                        listOfQueries.add(constructFinalMap(getMapWithQueries(withTZ, WithoutTZ), operators.getString(i)));
                    }

                    queries.put(key, listOfQueries);
                }

                if (key.equals(normalOperators[6]) || key.equals(normalOperators[7]) || key.equals(normalOperators[8])) {

                    JSONObject splConfig = currConfig.getJSONObject(TYPE2).getJSONObject(CONFIG);
                    splConfig.put(VALUE, filterValues.getString(key));

                    // WITH_TZ
                    JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                    withTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withTZReportObj, withTzFiltersMap, splConfig);

                    // WITHOUT_TZ
                    JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                    withoutTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, splConfig);

                    String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                    String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                    listOfQueries.add(constructFinalMap(getMapWithQueries(withTZ, WithoutTZ), TIMESTAMP_ONLY));
                    queries.put(key, listOfQueries);
                }

                if (key.equals(normalOperators[9])) {

                    filterValue.put(FROM_VALUE, filterValues.getString(IS_BETWEEN_FROM_DATE));
                    filterValue.put(TO_VALUE, filterValues.getString(IS_BETWEEN_TO_DATE));

                    // WITH_TZ
                    JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                    withTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                    // WITHOUT_TZ
                    JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                    withoutTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);


                    String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                    String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                    listOfQueries.add(constructFinalMap(getMapWithQueries(withTZ, WithoutTZ), TIMESTAMP_ONLY));
                    queries.put(key, listOfQueries);
                }
            }
        }

        Map<String, List<Map<String, Map<String, JSONObject>>>> unresolvedTickets = new HashMap<>();

        if (flowType.equals(UNRESOLVED_TICKETS)) {

            for (String key : unresolvedOperators) {

                System.out.println("***************" + key + "********************");

                JSONObject currConfig = filterConfig.getJSONObject(key);
                JSONArray operators = !key.equals(unresolvedOperators[4]) ? currConfig.getJSONArray(KEYS) : null;
                JSONObject filterValue = currConfig.getJSONObject(CONFIG);

                List<Map<String, Map<String, JSONObject>>> listOfQueries = new ArrayList<>();

                if (key.equals(unresolvedOperators[0]) || key.equals(unresolvedOperators[3])) {

                    for (int i = 0; i < operators.length(); i++) {
                        filterValue.put(VALUE, operators.getString(i));

                        // WITH_TZ
                        JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                        withTzFiltersMap.put(OPERATOR, key);
                        withTzFiltersMap.put(FILTER_VALUE, filterValue);
                        setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                        // WITHOUT_TZ
                        JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                        withoutTzFiltersMap.put(OPERATOR, key);
                        withoutTzFiltersMap.put(FILTER_VALUE, filterValue);
                        setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                        String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                        String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                        listOfQueries.add(constructFinalMapForUnresolved(getMapWithQueries(new JSONObject(withTZ), new JSONObject(WithoutTZ)), operators.getString(i)));
                    }

                    unresolvedTickets.put(key, listOfQueries);
                }

                if (key.equals(unresolvedOperators[1]) || key.equals(unresolvedOperators[2])) {

                    for (int i = 0; i < operators.length(); i++) {

                        filterValue.put(VALUE, filterValues.getJSONObject(key).getString(operators.getString(i)));
                        filterValue.put(UNIT, operators.getString(i));

                        // WITH_TZ
                        JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                        withTzFiltersMap.put(OPERATOR, key);
                        setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                        // WITHOUT_TZ
                        JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                        withoutTzFiltersMap.put(OPERATOR, key);
                        setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                        String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                        String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                        listOfQueries.add(constructFinalMapForUnresolved(getMapWithQueries(new JSONObject(withTZ), new JSONObject(WithoutTZ)), operators.getString(i)));
                    }

                    unresolvedTickets.put(key, listOfQueries);
                }

                if (key.equals(unresolvedOperators[3])) {

                    JSONObject splConfig = currConfig.getJSONObject(TYPE2).getJSONObject(CONFIG);
                    splConfig.put(VALUE, filterValues.getString(key));

                    // WITH_TZ
                    JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                    withTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withTZReportObj, withTzFiltersMap, splConfig);

                    // WITHOUT_TZ
                    JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                    withoutTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, splConfig);

                    String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                    String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                    listOfQueries.add(constructFinalMapForUnresolved(getMapWithQueries(new JSONObject(withTZ), new JSONObject(WithoutTZ)), TIMESTAMP_ONLY));
                    unresolvedTickets.put(key, listOfQueries);
                }


                if (key.equals(unresolvedOperators[4])) {

                    filterValue.put(FROM_VALUE, filterValues.getString(IS_BETWEEN_FROM_DATE));
                    filterValue.put(TO_VALUE, filterValues.getString(IS_BETWEEN_TO_DATE));

                    // WITH_TZ
                    JSONObject withTzFiltersMap = getNormalMetricFiltersMap(withTZReportObj);
                    withTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withTZReportObj, withTzFiltersMap, filterValue);

                    // WITHOUT_TZ
                    JSONObject withoutTzFiltersMap = getNormalMetricFiltersMap(withoutTZReportObj);
                    withoutTzFiltersMap.put(OPERATOR, key);
                    setMetricFiltersMap(withoutTZReportObj, withoutTzFiltersMap, filterValue);

                    String withTZ = makeApiCall(withTZReportObj, YES, flowType);
                    String WithoutTZ = makeApiCall(withoutTZReportObj, NO, flowType);
                    listOfQueries.add(constructFinalMapForUnresolved(getMapWithQueries(new JSONObject(withTZ), new JSONObject(WithoutTZ)), TIMESTAMP_ONLY));
                    unresolvedTickets.put(key, listOfQueries);
                }

            }
        }

        if (!flowType.equals(UNRESOLVED_TICKETS)) {
            writeJsonToFile(OUTPUT_JSON, new JSONObject(queries));
        } else {
            writeJsonToFile(OUTPUT_JSON, new JSONObject(unresolvedTickets));
        }
    }

    // api call method which will return the query
    private static String makeApiCall(JSONObject obj, String opt, String flowType) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        if (opt.equals(YES)) {
            JSONObject tz = new JSONObject();
            tz.put(VALUE, TIMEZONE_VALUE);
            tz.put(ENABLED, TIMEZONE_ENABLED);
            obj.put(TIMEZONE, tz);
        }

        try {
            HttpRequest patchRequest = HttpRequest.newBuilder().
                    uri(new URI(opt.equals(YES) ? WITH_TZ_URL : WITHOUT_TZ_URL))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(obj.toString()))
                    .headers("x-auth-token", opt.equals(YES) ? WITH_TIMEZONE_AUTH : WITHOUT_TIMEZONE_AUTH, "Content-Type", "application/json; charset=utf-8")
                    .build();

            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject res = new JSONObject(response.body());
                if (flowType.equals(NORMAL)) {
                    String label = obj.getJSONArray(METRIC).getJSONObject(0).getString(LABEL);
                    return res.getJSONObject(RESPONSE).getJSONObject(METRIC_QURIES).getJSONObject(label).getString(QUERY);
                }

                if (flowType.equals(RESOLVED_TICKETS)) {
                    String label = obj.getJSONArray(METRIC).getJSONObject(0).getString(LABEL);
                    return res.getJSONObject(RESPONSE).getJSONObject(METRIC_QURIES).getJSONObject(label).getJSONObject(IMPROVEMENT_QUERY).getString(QUERY);
                }

                if (flowType.equals(UNRESOLVED_TICKETS)) {
                    String label = obj.getJSONArray(METRIC).getJSONObject(0).getString(LABEL);
                    String totalQuery = res.getJSONObject(RESPONSE).getJSONObject(METRIC_QURIES).getJSONObject(label).getString(QUERY);
                    String improvQuery = res.getJSONObject(RESPONSE).getJSONObject(METRIC_QURIES).getJSONObject(label).getJSONObject(IMPROVEMENT_QUERY).getString(QUERY);

                    Map<String, String> map = new HashMap<>();
                    map.put(TOTAL_QUERY, totalQuery);
                    map.put(IMPROV_QUERY, improvQuery);

                    return new JSONObject(map).toString();

                }

            } else {
                throw new Exception(response.body());
            }


        } catch (URISyntaxException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // this is to read the json file from res
    private static JSONObject jsonFileReader(String fileNameOrPath) {

        try (FileReader fileReader = new FileReader(fileNameOrPath)) {
            return new JSONObject(new JSONTokener(fileReader));
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // this is to get the filtersMap from the Normal Metric
    private static JSONObject getNormalMetricFiltersMap(JSONObject obj) {
        return obj.getJSONArray(METRIC).getJSONObject(0).getJSONObject(NESTED_FILTER).getJSONObject(FILTERS_MAP).getJSONObject(ONE);
    }

    // this method is to set the normal metric filters map
    private static void setMetricFiltersMap(JSONObject obj, JSONObject map, JSONObject value) {
        map.put(FILTER_VALUE, value);
        obj.getJSONArray(METRIC).getJSONObject(0).getJSONObject(NESTED_FILTER).getJSONObject(FILTERS_MAP).put(ONE, map);
    }

    // this is to create a map dynamically
    private static Map<String, String> getMapWithQueries(String withTZ, String withoutTZ) {

        Map<String, String> map = new HashMap<>();
        map.put(WITHTZ, withTZ);
        map.put(WIHTOUTTZ, withoutTZ);
        return map;
    }

    // this is overloading of the above method

    private static Map<String, JSONObject> getMapWithQueries(JSONObject withTZ, JSONObject withoutTZ) {

        Map<String, JSONObject> map = new HashMap<>();
        map.put(WITHTZ, withTZ);
        map.put(WIHTOUTTZ, withoutTZ);
        return map;
    }

    // this method is to get the map with operator
    private static Map<String, Map<String, String>> constructFinalMap(Map<String, String> map, String operator) {

        Map<String, Map<String, String>> finalMap = new HashMap<>();
        finalMap.put(operator, map);

        return finalMap;

    }

    // this method is overloading of above method
    private static Map<String, Map<String, JSONObject>> constructFinalMapForUnresolved(Map<String, JSONObject> map, String operator) {

        Map<String, Map<String, JSONObject>> finalMap = new HashMap<>();
        finalMap.put(operator, map);

        return finalMap;

    }

    // to write it in json file
    private static void writeJsonToFile(String filePath, JSONObject queries) {
        File file = new File(filePath);

        // Delete the file if it exists
        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(queries.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}