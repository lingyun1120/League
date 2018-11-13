package com.xtp.league.pojo;

import java.util.List;

public class GankDetailBean extends BaseBean {


    /**
     * "category":["Android", "拓展资源", "App", "前端", "iOS", "瞎推荐", "休息视频", "福利" ]
     */

    private ResultsBean results;
    private List<String> category;

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
        private List<GankBean> Android;
        private List<GankBean> iOS;
        private List<GankBean> 前端;
        private List<GankBean> 瞎推荐;
        private List<GankBean> 拓展资源;
        private List<GankBean> App;
        private List<GankBean> 休息视频;
        private List<GankBean> 福利;

        public List<GankBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<GankBean> Android) {
            this.Android = Android;
        }

        public List<GankBean> getApp() {
            return App;
        }

        public void setApp(List<GankBean> App) {
            this.App = App;
        }

        public List<GankBean> getIOS() {
            return iOS;
        }

        public void setIOS(List<GankBean> iOS) {
            this.iOS = iOS;
        }

        public List<GankBean> getWeb() {
            return 前端;
        }

        public void setWeb(List<GankBean> web) {
            this.前端 = web;
        }

        public List<GankBean> getVideo() {
            return 休息视频;
        }

        public void setVideo(List<GankBean> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public List<GankBean> getResource() {
            return 拓展资源;
        }

        public void setResource(List<GankBean> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }

        public List<GankBean> getRecommend() {
            return 瞎推荐;
        }

        public void setRecommend(List<GankBean> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public List<GankBean> getWelfare() {
            return 福利;
        }

        public void setWelfare(List<GankBean> 福利) {
            this.福利 = 福利;
        }
    }
}
