package com.chuanglan.freeswitch.dynamic.loader.core.model.request;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.base.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DirectoryRequest extends BaseRequest {

    /********** Applicable common fields START **********/

    private String sip_contact_user;

    private String sip_contact_host;

    private String sip_auth_username;

    private String sip_auth_response;

    private String sip_auth_qop;

    private String sip_auth_nonce;

    private String sip_auth_nc;

    private String sip_auth_cnonce;

    private String sip_auth_method;

    private String sip_profile;

    private String sip_to_user;

    private String sip_to_port;

    private String sip_user_agent;

    /********** Applicable common fields END **********/

    private String sip_to_host;

    /********** Non-applicable common fields START **********/

    private String hostname;

    private String sip_from_host;

    private String sip_request_host;

    private String sip_auth_realm;

    private String key_name;

    private String key_value;

    private String sip_auth_uri;

    private String key;

    private String tag_name;

    /********** Non-applicable common fields END **********/

    private String profile;

    private String purpose;

    private String section;

    private String action;

    private String domain;

    private String user;

    private String ip;

    private String sip_via_protocol;

    private String sip_from_user;

    private String sip_call_id;

    private String client_port;

    @JsonProperty("Event-Name")
    private String event_name;

    @JsonProperty("Core-UUID")
    private String core_uuid;

    @JsonProperty("FreeSWITCH-Hostname")
    private String freeswitch_hostname;

    @JsonProperty("FreeSWITCH-Switchname")
    private String freeswitch_switchname;

    @JsonProperty("FreeSWITCH-IPv4")
    private String freeswitch_ipv4;

    @JsonProperty("FreeSWITCH-IPv6")
    private String freeswitch_ipv6;

    @JsonProperty("Event-Date-Local")
    private String event_date_local;

    @JsonProperty("Event-Date-GMT")
    private String event_date_gmt;

    @JsonProperty("Event-Date-Timestamp")
    private String event_date_timestamp;

    @JsonProperty("Event-Calling-File")
    private String event_calling_file;

    @JsonProperty("Event-Calling-Function")
    private String event_calling_function;

    @JsonProperty("Event-Calling-Line-Number")
    private String event_calling_line_number;

    @JsonProperty("Event-Sequence")
    private String event_sequence;
}
