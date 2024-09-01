package com.timezone;

public interface Constants {


    // Operators
    String[] normalOperators = {"IS_ON", "DAY_IS", "IN_THE_LAST", "IN_THE_NEXT", "NOT_IN_THE_LAST", "NOT_IN_THE_NEXT", "AFTER", "BEFORE", "SINCE", "IS_BETWEEN"};
    String[] unresolvedOperators = {"IS_ON", "IN_THE_LAST", "IN_THE_NEXT", "SINCE", "IS_BETWEEN"};

    // app Name
    String APP_NAME = "freshservice";

    // flow types
    String NORMAL = "NORMAL";
    String RESOLVED_TICKETS = "RESOLVED_TICKETS";
    String UNRESOLVED_TICKETS = "UNRESOLVED_TICKETS";

    // other's
    String KEYS = "keys";
    String CONFIG = "config";
    String METRIC = "metrics";
    String NESTED_FILTER = "nestedFilter";
    String FILTERS_MAP = "filtersMap";
    String ONE = "1";
    String OPERATOR = "operator";
    String VALUE = "value";

    String FILTER_VALUE = "filterValue";
    String TRANSLATED_LABEL = "translatedLabel";
    String UNIT = "unit";
    String TYPE2 = "type2";
    String FROM_VALUE = "fromValue";
    String TO_VALUE = "toValue";
    String IS_BETWEEN_FROM_DATE = "IS_BETWEEN_FROM_DATE";
    String IS_BETWEEN_TO_DATE = "IS_BETWEEN_TO_DATE";
    String YES = "yes";
    String NO = "no";
    String TIMEZONE = "timezone";
    String ENABLED = "enabled";
    String METRIC_QURIES = "metricQueries";
    String RESPONSE = "response";
    String QUERY = "query";
    String LABEL = "label";
    String WITHTZ = "withTZ";
    String WIHTOUTTZ = "withOutTZ";
    String TIMESTAMP_ONLY = "TIMESTAMP_ONLY";
    String IMPROVEMENT_QUERY = "improvementQuery";
    String TOTAL_QUERY = "TOTAL_QUERY";
    String IMPROV_QUERY = "IMPROV_QUERY";


    // path's
    String CONFIG_JSON = "src/main/resources/filter/filter_config.json";
    String FILTER_VALUES = "src/main/resources/filter/filter_values.json";
    String OUTPUT_JSON = "src/main/resources/web/" + UNRESOLVED_TICKETS + "_output.json";

    // reportGroup path's
    String OBJ_NAME = "unresolved_tickets";
    String WITH_TZ_REPORT_GROUP_OBJ = "src/main/resources/reportGroup/withTZ/" + OBJ_NAME + ".json";
    String WITHOUT_TZ_REPORT_GROUP_OBJ = "src/main/resources/reportGroup/withoutTZ/" + OBJ_NAME + ".json";


    //stack url's
    String WITH_TIMEZONE_URL = "http://localhost:8080/";
    String WITHOUT_TIMEZONE_URL = "https://staging.freshreports.com/";

    // x-auth-token's
    String WITH_TIMEZONE_AUTH = "";

    String WITHOUT_TIMEZONE_AUTH = "";

    // ReportGroup id's
    // Normal metric
    String WITH_TIMEZONE_NORMAL_REPORTGROUP = "362141";
    String WITHOUT_TIMEZONE_NORMAL_REPORTGROUP = "381045";

    // Report id's
    // Normal metric
    String WITH_TIMEZONE_NORMAL_REPORT = "8002512";
    String WITHOUT_TIMEZONE_NORMAL_REPORT = "8571779";

    // final URL's
    String WITH_TZ_URL = WITH_TIMEZONE_URL + "api/v1/reportgroups/" + WITH_TIMEZONE_NORMAL_REPORTGROUP + "/reports/" + WITH_TIMEZONE_NORMAL_REPORT + "/modify?limit=100&offset=0&pageIndex=0";
    String WITHOUT_TZ_URL = WITHOUT_TIMEZONE_URL + "api/v1/reportgroups/" + WITHOUT_TIMEZONE_NORMAL_REPORTGROUP + "/reports/" + WITHOUT_TIMEZONE_NORMAL_REPORT + "/modify?limit=100&offset=0&pageIndex=0";

    // TIMEZONE's CONFIG
    String TIMEZONE_VALUE = "America/New_York";
    boolean TIMEZONE_ENABLED = true;
}
