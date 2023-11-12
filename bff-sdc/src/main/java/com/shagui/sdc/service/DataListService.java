package com.shagui.sdc.service;

import java.util.List;

public interface DataListService {
    List<String> dataLists();

    List<String> dataListValues(String key);
}
