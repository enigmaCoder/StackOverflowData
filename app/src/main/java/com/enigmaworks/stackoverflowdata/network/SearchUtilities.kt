package com.enigmaworks.stackoverflowdata.network

import com.enigmaworks.stackoverflowdata.ui.main.InputTypes

enum class FilterParameters(val filterKeyValue: String,val inputTypes: InputTypes){
    ORDER("order",InputTypes.TEXT),
    SORT("sort",InputTypes.TEXT),
    PAGE("page",InputTypes.TEXT),
    PAGE_SIZE("pagesize",InputTypes.NUMBER),
    FROM_DATE("fromDate",InputTypes.DATE),
    TO_DATE("toDate",InputTypes.DATE),
    MIN("min",InputTypes.NUMBER),
    MAX("max",InputTypes.NUMBER),
    TAGGED("tagged",InputTypes.TEXT),
    NOT_TAGGED("nottagged",InputTypes.TEXT),
    IN_TITLE("intitle",InputTypes.TEXT)
}