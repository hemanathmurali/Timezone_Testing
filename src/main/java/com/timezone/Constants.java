package com.timezone;

public interface Constants {


    // Operators
    public static String[] normalOperators = {"IS_ON", "DAY_IS", "IN_THE_LAST", "IN_THE_NEXT", "NOT_IN_THE_LAST", "NOT_IN_THE_NEXT", "AFTER", "BEFORE", "SINCE", "IS_BETWEEN"};

    // app Name
    public static final String APP_NAME = "freshservice";

    //stack url's
    public static final String WITH_TIMEZONE_URL = "http://localhost:8080/";
    public static final String WITHOUT_TIMEZONE_URL = "https://fv-prestaging.freshreports.com/";

    // x-auth-token's
    public static final String WITH_TIMEZONE_AUTH = "";
    public static final String WITHOUT_TIMEZONE_AUTH = "";

    // ReportGroup id's
    // Normal metric
    public static final String WITH_TIMEZONE_NORMAL_REPORTGROUP = "340862";
    public static final String WITHOUT_TIMEZONE_NORMAL_REPORTGROUP = "373656";

    // Report id's
    // Normal metric
    public static final String WITH_TIMEZONE_NORMAL_REPORT = "7306579";
    public static final String WITHOUT_TIMEZONE_NORMAL_REPORT = "8095574";


    // other's
    public static final String KEYS = "keys";
    public static final String CONFIG = "config";
    public static final String METRIC = "metrics";
    public static final String NESTED_FILTER = "nestedFilter";
    public static final String FILTERS_MAP = "filtersMap";
    public static final String ONE = "1";
    public static final String OPERATOR = "operator";
    public static final String VALUE = "value";
    public static final String NORMAL = "Normal";
    public static final String FILTER_VALUE = "filterValue";
    public static final String TRANSLATED_LABEL = "translatedLabel";
    public static final String UNIT = "unit";
    public static final String TYPE2 = "type2";
    public static final String FROM_VALUE = "fromValue";
    public static final String TO_VALUE = "toValue";
    public static final String IS_BETWEEN_FROM_DATE = "IS_BETWEEN_FROM_DATE";
    public static final String IS_BETWEEN_TO_DATE = "IS_BETWEEN_TO_DATE";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String TIMEZONE = "timezone";
    public static final String ENABLED = "enabled";
    public static final String METRIC_QURIES = "metricQueries";
    public static final String RESPONSE = "response";
    public static final String QUERY = "query";
    public static final String LABEL = "label";
    public static final String WITHTZ = "withTZ";
    public static final String WIHTOUTTZ = "withOutTZ";
    public static final String TIMESTAMP_ONLY = "TIMESTAMP_ONLY";
    public static final String RESOLVED_TICKETS = "RESOLVED_TICKETS";
    public static final String IMPROVEMENT_QUERY = "improvementQuery";

    // final URL's
    public static final String WITH_TZ_URL = WITH_TIMEZONE_URL + "api/reportgroups/" + WITH_TIMEZONE_NORMAL_REPORTGROUP + "/reports/" + WITH_TIMEZONE_NORMAL_REPORT + "/modify?limit=100&offset=0&pageIndex=0";
    public static final String WITHOUT_TZ_URL = WITHOUT_TIMEZONE_URL + "api/reportgroups/" + WITHOUT_TIMEZONE_NORMAL_REPORTGROUP + "/reports/" + WITHOUT_TIMEZONE_NORMAL_REPORT + "/modify?limit=100&offset=0&pageIndex=0";
}
