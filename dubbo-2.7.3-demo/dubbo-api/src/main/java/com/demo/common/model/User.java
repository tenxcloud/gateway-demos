/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2022 TenxCloud. All Rights Reserved.
 */
package com.demo.common.model;

import lombok.Data;

@Data
public class User implements java.io.Serializable{
    private String name;
    private Address address;
}
