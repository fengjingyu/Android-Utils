// AIDLBean.aidl
package com.jingyu.test.service;

// 之前AIDLBean是放在java/package/service下的,而AIDLBean.aidl是放在aidl/package下的,
// 编译出错,找不到文件,然后在aidl建了serice包,并把aidl移到service文件夹里,编译可以通过
parcelable AIDLBean;
