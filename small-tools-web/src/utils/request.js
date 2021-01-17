import axios from "axios";
import { MessageBox, Message } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
});

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['ZQ-X-TOKEN'] is a custom headers key
      // please modify it according to the actual situation
      config.headers["ZQ-X-TOKEN"] = getToken();
    }
    return config;
  },
  error => {
    // do something with request error
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data;
    if (res.code !== 200) {
      Message({
        message: res.msg || "接口请求报错",
        type: "error",
        duration: 5 * 1000
      });
      if (res.code === -1) {
        // token过期
        // to re-login
        MessageBox.confirm("您的登录账号已失效，请重新登录", {
          confirmButtonText: "再次登录",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          store.dispatch("user/resetToken").then(() => {
            location.reload();
          });
        });
      }
      return Promise.reject(new Error(res.msg || "Error"));
    } else {
      return res;
    }
  },
  error => {
    // 接口反爬虫处理 509状态码
    // if (error.response.status === 509) {
    //   const html = error.response.data
    //   const verifyWindow = window.open('', '_blank', 'height=400,width=560')
    //   verifyWindow.document.write(html)
    //   verifyWindow.document.getElementById('baseUrl').value = process.env.VUE_APP_BASE_API
    // }
    console.log("err" + error); // for debug
    Message({
      message: "网络异常，请稍后再试",
      type: "error",
      duration: 5 * 1000
    });
    // this.submitFail(res.msg)
    return Promise.reject(error);
  }
);

export default service;
