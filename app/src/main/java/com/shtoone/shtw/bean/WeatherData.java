package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/8/22.
 */
public class WeatherData {

        /**
         * msg : success
         * result : [{"airCondition":"良","city":"武汉","coldIndex":"低发期","date":"2016-08-04","distrct":"武汉","dressingIndex":"薄短袖类","exerciseIndex":"不适宜","future":[{"date":"2016-08-04","dayTime":"阵雨","night":"多云","temperature":"30°C / 26°C","week":"今天","wind":"东北风 小于3级"},{"date":"2016-08-05","dayTime":"阵雨","night":"阵雨","temperature":"32°C / 26°C","week":"星期五","wind":"东风 小于3级"},{"date":"2016-08-06","dayTime":"阵雨","night":"阵雨","temperature":"32°C / 26°C","week":"星期六","wind":"西北风 小于3级"},{"date":"2016-08-07","dayTime":"阵雨","night":"多云","temperature":"34°C / 26°C","week":"星期日","wind":"北风 小于3级"},{"date":"2016-08-08","dayTime":"阵雨","night":"多云","temperature":"35°C / 25°C","week":"星期一","wind":"北风 小于3级"},{"date":"2016-08-09","dayTime":"多云","night":"多云","temperature":"34°C / 26°C","week":"星期二","wind":"东北风 小于3级"},{"date":"2016-08-10","dayTime":"雷雨","night":"雷雨","temperature":"33°C / 26°C","week":"星期三","wind":"东南偏东风 2级"},{"date":"2016-08-11","dayTime":"雷雨","night":"局部多云","temperature":"33°C / 26°C","week":"星期四","wind":"东南偏东风 2级"},{"date":"2016-08-12","dayTime":"零散雷雨","night":"局部多云","temperature":"34°C / 27°C","week":"星期五","wind":"东南偏东风 2级"},{"date":"2016-08-13","dayTime":"零散雷雨","night":"局部多云","temperature":"34°C / 27°C","week":"星期六","wind":"东风 2级"}],"humidity":"湿度：97%","pollutionIndex":"53","province":"湖北","sunrise":"05:43","sunset":"19:15","temperature":"26℃","time":"05:52","updateTime":"20160804061616","washIndex":"不适宜","weather":"阴","week":"周四","wind":"北风2级"}]
         * retCode : 200
         */

        private String msg;
        private String retCode;
        private List<ResultEntity> result;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public String getMsg() {
            return msg;
        }

        public String getRetCode() {
            return retCode;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public static class ResultEntity {
            /**
             * airCondition : 良
             * city : 武汉
             * coldIndex : 低发期
             * date : 2016-08-04
             * distrct : 武汉
             * dressingIndex : 薄短袖类
             * exerciseIndex : 不适宜
             * future : [{"date":"2016-08-04","dayTime":"阵雨","night":"多云","temperature":"30°C / 26°C","week":"今天","wind":"东北风 小于3级"},{"date":"2016-08-05","dayTime":"阵雨","night":"阵雨","temperature":"32°C / 26°C","week":"星期五","wind":"东风 小于3级"},{"date":"2016-08-06","dayTime":"阵雨","night":"阵雨","temperature":"32°C / 26°C","week":"星期六","wind":"西北风 小于3级"},{"date":"2016-08-07","dayTime":"阵雨","night":"多云","temperature":"34°C / 26°C","week":"星期日","wind":"北风 小于3级"},{"date":"2016-08-08","dayTime":"阵雨","night":"多云","temperature":"35°C / 25°C","week":"星期一","wind":"北风 小于3级"},{"date":"2016-08-09","dayTime":"多云","night":"多云","temperature":"34°C / 26°C","week":"星期二","wind":"东北风 小于3级"},{"date":"2016-08-10","dayTime":"雷雨","night":"雷雨","temperature":"33°C / 26°C","week":"星期三","wind":"东南偏东风 2级"},{"date":"2016-08-11","dayTime":"雷雨","night":"局部多云","temperature":"33°C / 26°C","week":"星期四","wind":"东南偏东风 2级"},{"date":"2016-08-12","dayTime":"零散雷雨","night":"局部多云","temperature":"34°C / 27°C","week":"星期五","wind":"东南偏东风 2级"},{"date":"2016-08-13","dayTime":"零散雷雨","night":"局部多云","temperature":"34°C / 27°C","week":"星期六","wind":"东风 2级"}]
             * humidity : 湿度：97%
             * pollutionIndex : 53
             * province : 湖北
             * sunrise : 05:43
             * sunset : 19:15
             * temperature : 26℃
             * time : 05:52
             * updateTime : 20160804061616
             * washIndex : 不适宜
             * weather : 阴
             * week : 周四
             * wind : 北风2级
             */

            private String airCondition;
            private String city;
            private String coldIndex;
            private String date;
            private String distrct;
            private String dressingIndex;
            private String exerciseIndex;
            private String humidity;
            private String pollutionIndex;
            private String province;
            private String sunrise;
            private String sunset;
            private String temperature;
            private String time;
            private String updateTime;
            private String washIndex;
            private String weather;
            private String week;
            private String wind;
            private List<FutureEntity> future;

            public void setAirCondition(String airCondition) {
                this.airCondition = airCondition;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setColdIndex(String coldIndex) {
                this.coldIndex = coldIndex;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public void setDistrct(String distrct) {
                this.distrct = distrct;
            }

            public void setDressingIndex(String dressingIndex) {
                this.dressingIndex = dressingIndex;
            }

            public void setExerciseIndex(String exerciseIndex) {
                this.exerciseIndex = exerciseIndex;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public void setPollutionIndex(String pollutionIndex) {
                this.pollutionIndex = pollutionIndex;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setWashIndex(String washIndex) {
                this.washIndex = washIndex;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public void setFuture(List<FutureEntity> future) {
                this.future = future;
            }

            public String getAirCondition() {
                return airCondition;
            }

            public String getCity() {
                return city;
            }

            public String getColdIndex() {
                return coldIndex;
            }

            public String getDate() {
                return date;
            }

            public String getDistrct() {
                return distrct;
            }

            public String getDressingIndex() {
                return dressingIndex;
            }

            public String getExerciseIndex() {
                return exerciseIndex;
            }

            public String getHumidity() {
                return humidity;
            }

            public String getPollutionIndex() {
                return pollutionIndex;
            }

            public String getProvince() {
                return province;
            }

            public String getSunrise() {
                return sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public String getTemperature() {
                return temperature;
            }

            public String getTime() {
                return time;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public String getWashIndex() {
                return washIndex;
            }

            public String getWeather() {
                return weather;
            }

            public String getWeek() {
                return week;
            }

            public String getWind() {
                return wind;
            }

            public List<FutureEntity> getFuture() {
                return future;
            }

            public static class FutureEntity {
                /**
                 * date : 2016-08-04
                 * dayTime : 阵雨
                 * night : 多云
                 * temperature : 30°C / 26°C
                 * week : 今天
                 * wind : 东北风 小于3级
                 */

                private String date;
                private String dayTime;
                private String night;
                private String temperature;
                private String week;
                private String wind;

                public void setDate(String date) {
                    this.date = date;
                }

                public void setDayTime(String dayTime) {
                    this.dayTime = dayTime;
                }

                public void setNight(String night) {
                    this.night = night;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getDate() {
                    return date;
                }

                public String getDayTime() {
                    return dayTime;
                }

                public String getNight() {
                    return night;
                }

                public String getTemperature() {
                    return temperature;
                }

                public String getWeek() {
                    return week;
                }

                public String getWind() {
                    return wind;
                }

        }
    }

}
