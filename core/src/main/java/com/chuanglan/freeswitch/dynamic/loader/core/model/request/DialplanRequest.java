package com.chuanglan.freeswitch.dynamic.loader.core.model.request;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.base.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DialplanRequest extends BaseRequest {

    private String hostname;

    private String section;

    private String tag_name;

    private String key_name;

    private String key_value;

    private String variable_direction;

    private String variable_uuid;

    private String variable_session_id;

    private String variable_sip_from_user;

    private String variable_sip_from_uri;

    private String variable_sip_from_host;

    private String variable_video_media_flow;

    private String variable_audio_media_flow;

    private String variable_channel_name;

    private String variable_sip_call_id;

    private String variable_ep_codec_string;

    private String variable_sip_local_network_addr;

    private String variable_sip_network_ip;

    private String variable_sip_network_port;

    private String variable_sip_invite_stamp;

    private String variable_sip_received_ip;

    private String variable_sip_received_port;

    private String variable_sip_via_protocol;

    private String variable_sip_from_user_stripped;

    private String variable_sip_from_tag;

    private String variable_sofia_profile_name;

    private String variable_recovery_profile_name;

    private String variable_sip_full_via;

    private String variable_sip_from_display;

    private String variable_sip_full_from;

    private String variable_sip_to_display;

    private String variable_sip_full_to;

    private String variable_sip_allow;

    private String variable_sip_req_user;

    private String variable_sip_req_uri;

    private String variable_sip_req_host;

    private String variable_sip_to_user;

    private String variable_sip_to_uri;

    private String variable_sip_to_host;

    private String variable_sip_contact_user;

    private String variable_sip_contact_port;

    private String variable_sip_contact_uri;

    private String variable_sip_contact_host;

    private String variable_rtp_use_codec_string;

    private String variable_sip_user_agent;

    private String variable_sip_via_host;

    private String variable_sip_via_port;

    private String variable_sip_via_rport;

    private String variable_max_forwards;

    private String variable_presence_id;

    private String variable_sip_nat_detected;

    private String variable_switch_r_sdp;

    private String variable_endpoint_disposition;

    private String variable_call_uuid;

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

    @JsonProperty("Event_Calling_File")
    private String event_calling_file;

    @JsonProperty("Event-Calling-Function")
    private String event_calling_function;

    @JsonProperty("Event-Calling-Line-Number")
    private String event_calling_line_number;

    @JsonProperty("Event-Sequence")
    private String event_sequence;

    @JsonProperty("Channel-State")
    private String channel_state;

    @JsonProperty("Channel-Call-State")
    private String channel_call_state;

    @JsonProperty("Channel-State-Number")
    private String channel_state_number;

    @JsonProperty("Channel-Name")
    private String channel_name;

    @JsonProperty("Unique-ID")
    private String unique_id;

    @JsonProperty("Call-Direction")
    private String call_direction;

    @JsonProperty("Presence-Call-Direction")
    private String presence_call_direction;

    @JsonProperty("Channel-HIT-Dialplan")
    private String channel_hit_dialplan;

    @JsonProperty("Channel-Presence-ID")
    private String channel_presence_id;

    @JsonProperty("Channel-Call-UUID")
    private String channel_call_uuid;

    @JsonProperty("Answer-State")
    private String answer_state;

    @JsonProperty("Caller-Direction")
    private String caller_direction;

    @JsonProperty("Caller-Logical-Direction")
    private String caller_logical_direction;

    @JsonProperty("Caller-Username")
    private String caller_username;

    @JsonProperty("Caller-Dialplan")
    private String caller_dialplan;

    @JsonProperty("Caller-Caller-ID-Name")
    private String caller_caller_id_name;

    @JsonProperty("Caller-Caller-ID-Number")
    private String caller_caller_id_number;

    @JsonProperty("Caller-Orig-Caller-ID-Name")
    private String caller_orig_caller_id_name;

    @JsonProperty("Caller-Orig-Caller-ID-Number")
    private String caller_orig_caller_id_number;

    @JsonProperty("Caller-Network-Addr")
    private String caller_network_addr;

    @JsonProperty("Caller-ANI")
    private String caller_ani;

    @JsonProperty("Caller-Destination-Number")
    private String caller_destination_number;

    @JsonProperty("Caller-Unique-ID")
    private String caller_unique_id;

    @JsonProperty("Caller-Source")
    private String caller_source;

    @JsonProperty("Caller-Context")
    private String caller_context;

    @JsonProperty("Caller-Channel-Name")
    private String caller_channel_name;

    @JsonProperty("Caller-Profile-Index")
    private String caller_profile_index;

    @JsonProperty("Caller-Profile-Created-Time")
    private String caller_profile_created_time;

    @JsonProperty("Caller-Channel-Created-Time")
    private String caller_channel_created_time;

    @JsonProperty("Caller-Channel-Answered-Time")
    private String caller_channel_answered_time;

    @JsonProperty("Caller-Channel-Progress-Time")
    private String caller_channel_progress_time;

    @JsonProperty("Caller-Channel-Progress-Media-Time")
    private String caller_channel_progress_media_time;

    @JsonProperty("Caller-Channel-Hangup-Time")
    private String caller_channel_hangup_time;

    @JsonProperty("Caller-Channel-Transfer-Time")
    private String caller_channel_transfer_time;

    @JsonProperty("Caller-Channel-Resurrect-Time")
    private String caller_channel_resurrect_time;

    @JsonProperty("Caller-Channel-Bridged-Time")
    private String caller_channel_bridged_time;

    @JsonProperty("Caller-Channel-Last-Hold")
    private String caller_channel_last_hold;

    @JsonProperty("Caller-Channel-Hold-Accum")
    private String caller_channel_hold_accum;

    @JsonProperty("Caller-Screen-Bit")
    private String caller_screen_bit;

    @JsonProperty("Caller-Privacy-Hide-Name")
    private String caller_privacy_hide_name;

    @JsonProperty("Caller-Privacy-Hide-Number")
    private String caller_privacy_hide_number;

    @JsonProperty("Hunt-Direction")
    private String hunt_direction;

    @JsonProperty("Hunt-Logical-Direction")
    private String hunt_logical_direction;

    @JsonProperty("Hunt-Username")
    private String hunt_username;

    @JsonProperty("Hunt-Dialplan")
    private String hunt_dialplan;

    @JsonProperty("Hunt-Caller-ID-Name")
    private String hunt_caller_id_name;

    @JsonProperty("Hunt-Caller-ID-Number")
    private String hunt_caller_id_number;

    @JsonProperty("Hunt-Orig-Caller-ID-Name")
    private String hunt_orig_caller_id_name;

    @JsonProperty("Hunt-Orig-Caller-ID-Number")
    private String hunt_orig_caller_id_number;

    @JsonProperty("Hunt-Network-Addr")
    private String hunt_network_addr;

    @JsonProperty("Hunt-ANI")
    private String hunt_ani;

    @JsonProperty("Hunt-Destination-Number")
    private String hunt_destination_number;

    @JsonProperty("Hunt-Unique-ID")
    private String hunt_unique_id;

    @JsonProperty("Hunt-Source")
    private String hunt_source;

    @JsonProperty("Hunt-Context")
    private String hunt_context;

    @JsonProperty("Hunt-Channel-Name")
    private String hunt_channel_name;

    @JsonProperty("Hunt-Profile-Index")
    private String hunt_profile_index;

    @JsonProperty("Hunt-Profile-Created-Time")
    private String hunt_profile_created_time;

    @JsonProperty("Hunt-Channel-Created-Time")
    private String hunt_channel_created_time;

    @JsonProperty("Hunt-Channel-Answered-Time")
    private String hunt_channel_answered_time;

    @JsonProperty("Hunt-Channel-Progress-Time")
    private String hunt_channel_progress_time;

    @JsonProperty("Hunt-Channel-Progress-Media-Time")
    private String hunt_channel_progress_media_time;

    @JsonProperty("Hunt-Channel-Hangup-Time")
    private String hunt_channel_hangup_time;

    @JsonProperty("Hunt-Channel-Transfer-Time")
    private String hunt_channel_transfer_time;

    @JsonProperty("Hunt-Channel-Resurrect-Time")
    private String hunt_channel_resurrect_time;

    @JsonProperty("Hunt-Channel-Bridged-Time")
    private String hunt_channel_bridged_time;

    @JsonProperty("Hunt-Channel-Last-Hold")
    private String hunt_channel_last_hold;

    @JsonProperty("Hunt-Channel-Hold-Accum")
    private String hunt_channel_hold_accum;

    @JsonProperty("Hunt-Screen-Bit")
    private String hunt_screen_bit;

    @JsonProperty("Hunt-Privacy-Hide-Name")
    private String hunt_privacy_hide_name;

    @JsonProperty("Hunt-Privacy-Hide-Number")
    private String hunt_privacy_hide_number;

}
