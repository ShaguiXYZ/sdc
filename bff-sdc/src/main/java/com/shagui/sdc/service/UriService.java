package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.UriDTO;

public interface UriService {
    UriDTO componentUri(int componentId, String type);
}
