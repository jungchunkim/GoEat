package com.GOEAT.Go_Eat.DataType;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FoodPic {
    private Map<String, String> map = new HashMap<String, String>();
    private String foodName;
    private String foodSrc;
    private int cnt=0;
    static int cn = -1;
    String[] mKeys;

    public FoodPic() {
        cn++;
        String file = "갈비찜,https://recipe1.ezmember.co.kr/cache/recipe/2016/12/13/0f12719bc6d713fd727d5f1cd65ad6141.jpg\n" +
                "갈비,https://www.dailysecu.com/news/photo/201909/69290_64418_2221.jpg\n" +
                "나물밥,https://th1.tmon.kr/thumbs/image/95a/f9e/877/180f60958_700x700_95_FIT.jpg\n" +
                "전,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/11/7263705e8504004e76c8032753a104181.jpg\n" +
                "수제비,https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRzKhI2Gneee4bxPvM30hh5y5iQtehElLElbw&usqp=CAU\n" +
                "김밥,https://recipe1.ezmember.co.kr/cache/recipe/2016/06/29/e7401296033ab8e4297cd53d71e1bba91.jpg\n" +
                "전골,https://i.ytimg.com/vi/CeGfUvKBLKs/maxresdefault.jpg\n" +
                "청국장,https://s3.ap-northeast-2.amazonaws.com/meesig/v3/prod/image/item/main/215/3d20d252757d48f69c3f40404f11096b20170310151923\n" +
                "비빔밥,https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRj3X9tMAg-chptWHNFvGwiY4buQFnYZI4HOw&usqp=CAU\n" +
                "삼겹살,https://i.pinimg.com/originals/c4/c5/d5/c4c5d5d428634b5ddf1aabd9f98faf60.jpg\n" +
                "칼국수,https://www.menupan.com/restaurant/restimg/003/zzmenuimg/h4927636_z.jpg\n" +
                "수육,https://craftlog.com/m/i/6244757=s1280=h960\n" +
                "순두부,https://img.ssfshop.com/cmd/LB_500x660/src/https://img.ssfshop.com/goods/ORBR/20/02/12/GPAQ20021262406_0_ORGINL_20200423115609972.jpg\n" +
                "육개장,https://www.travie.com/news/photo/first/201607/img_19196_1.jpg\n" +
                "덮밥,https://recipe1.ezmember.co.kr/cache/recipe/2019/01/05/8cf7a3c0e113de435fa189b1d3e6984c1.jpg\n" +
                "백숙,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/04/d390442f043748c02a90beeabefc5dae1.jpg\n" +
                "제육 볶음,https://recipe1.ezmember.co.kr/cache/recipe/2018/02/12/5bde81aa7309397e596846010fb5e6f71.jpg\n" +
                "낚지볶음,https://recipe1.ezmember.co.kr/cache/recipe/2017/09/15/870609fd6e486df1f8f3e09f322aedeb1.jpg\n" +
                "쭈꾸미 볶음,https://recipe1.ezmember.co.kr/cache/recipe/2017/06/21/b30caebf25bda7193fb1c345f082f8d41.jpg\n" +
                "계란 요리,https://i.ytimg.com/vi/1Vqckf57lvk/maxresdefault.jpg\n" +
                "생선 조림,https://recipe1.ezmember.co.kr/cache/recipe/2018/12/10/b4ab2144a8b58f8a5d055c37cf7563941.jpg\n" +
                "생선 구이,https://m.thefreshop.co.kr/web/product/big/4756748899_Big.jpg\n" +
                "돌솥밥,https://cdn.crowdpic.net/list-thumb/thumb_l_653D49286C7ED740EAF2E18E42541E6E.jpg\n" +
                "대구탕,https://recipe1.ezmember.co.kr/cache/recipe/2019/06/17/9618f566128cca0bb933c4b727d378f91.jpg\n" +
                "떡갈비,https://craftlog.com/m/i/5784724=s1280=h960\n" +
                "떡볶이,https://img3.tmon.kr/cdn3/deals/2019/07/23/2280944206/front_e7b5f_nfan1.jpg\n" +
                "주먹밥,https://i.ytimg.com/vi/Gmyh4cgAlK8/maxresdefault.jpg\n" +
                "초밥,https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000881/img/basic/a0000881_main.jpg?20200630185341&q=80&rw=750&rh=536\n" +
                "라면,https://www.sisajournal.com/news/photo/201905/185983_90773_1638.jpg\n" +
                "돈까스,https://th3.tmon.kr/thumbs/image/6ea/082/d57/2906e06a0_700x700_95_FIT.jpg\n" +
                "튀김,https://craftlog.com/m/i/5842264=s1280=h960\n" +
                "롤,https://recipe1.ezmember.co.kr/cache/recipe/2016/04/13/0749ee3997e00ff4e945f09d5c83e31a1.jpg\n" +
                "만두,https://pds.joins.com//news/component/htmlphoto_mmdata/201802/07/12621b47-8458-4025-9c77-e6930ac4c3ab.jpg\n" +
                "순대,https://product.hstatic.net/1000343598/product/cb363ba3c9e341399619192921b186da_fce09e3e91964f5087ebe7845c9d7a18_grande.jpg\n" +
                "오뎅,https://ai.esmplus.com/foodjang01/images/221500311_500px.jpg\n" +
                "잡채,https://t1.daumcdn.net/cfile/tistory/2329B43F550456482C\n" +
                "마요,https://craftlog.com/m/i/5936362=s1280=h960\n" +
                "완자,https://recipe1.ezmember.co.kr/cache/recipe/2017/11/08/0598b87df8bec5171ccf1236f5dd80bd1.jpg\n" +
                "토스트,https://t1.daumcdn.net/cfile/tistory/9998B74F5A529F061C\n" +
                "피자,https://img.huffingtonpost.com/asset/5bed0be3220000bc04de3cbc.jpeg?cache=s6tbBWRVTk&ops=crop_495_410_4396_2730%2Cscalefit_720_noupscale\n" +
                "라볶이,https://recipe1.ezmember.co.kr/cache/recipe/2018/04/12/f4b3e8bfca87fe71f9b6764b5703e7181.jpg\n" +
                "우동,https://recipe1.ezmember.co.kr/cache/recipe/2015/06/08/0fd69039751d2e9d6649ae63bca07cd2.jpg\n" +
                "쫄면,https://www.nadrifood.co.kr/wp-content/uploads/2017/12/goods_01.jpg\n" +
                "물냉면,https://thumbnail.10x10.co.kr/webimage/image/basic600/232/B002324904.jpg?cmd=thumb&w=500&h=500&fit=true&ws=false\n" +
                "소바,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/25/2e956f8217b643fef8b94370dfd6ceda1.jpg\n" +
                "도시락,https://webzine.nfm.go.kr/wp-content/uploads/2019/04/1170x765_%EB%8F%84%EC%8B%9C%EB%9D%BD-%ED%99%95%EC%A0%95.jpg\n" +
                "볶음밥,https://recipe1.ezmember.co.kr/cache/recipe/2015/08/27/932b0eac49b0f341ee9b91553d84d9b91.jpg\n" +
                "찌개,https://recipe1.ezmember.co.kr/cache/recipe/2017/04/26/ddd495fd432955701068e1a21a0d33211.jpg\n" +
                "닭볶음탕,https://recipe1.ezmember.co.kr/cache/recipe/2016/11/23/a6446c45269c63b1c5a1e7d2e7ccd2cf1.jpg\n" +
                "닭발,https://cdn.imweb.me/thumbnail/20180908/5b92b390e58ea.jpg\n" +
                "내장,https://th4.tmon.kr/thumbs/image/f2f/494/688/7e915e181_700x700_95_FIT.jpg\n" +
                "치즈그라탕,https://recipe1.ezmember.co.kr/cache/recipe/2017/03/17/cb32265fb4466d7a9024ff1d7877101e1.jpg\n" +
                "꿔바로우,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "도삭면,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "탕수육,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "깐풍기,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "짜장면,https://recipe1.ezmember.co.kr/cache/recipe/2020/06/04/d96e1e81ecc8d86c922d486ec6eec4da1_m.jpg\n" +
                "짬뽕,https://craftlog.com/m/i/6107302=s1280=h960\n" +
                "유린기,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "라조기,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "고추잡채,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "양장피,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "팔보채,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "새우 요리,https://t1.daumcdn.net/cfile/tistory/9942723E5AFBA3F230\n" +
                "볶음밥,짬뽕,https://craftlog.com/m/i/6107302=s1280=h960\n" +
                "볶음밥,짜장면,https://recipe1.ezmember.co.kr/cache/recipe/2020/06/04/d96e1e81ecc8d86c922d486ec6eec4da1_m.jpg\n" +
                "탕수육,짜장면,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "탕수육,짬뽕,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "탕수육,볶음밥,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "냉콩국수,https://recipe1.ezmember.co.kr/cache/recipe/2018/07/14/7ed726074031a11480d1ed901c5b92211.jpg\n" +
                "쌀국수,https://t1.daumcdn.net/liveboard/realfood/8f543966d8b7478e905ec2e65433a596.JPG\n" +
                "마라탕,https://img1.tmon.kr/cdn3/deals/2019/05/24/2099407062/original_2099407062_front_935dc_1558688230production.jpg\n" +
                "볶음면,https://images-na.ssl-images-amazon.com/images/I/71tPyDx01JL._AC_SX679_.jpg\n" +
                "마라샹궈,https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2mg6/image/9d8TOY3Yp6nCKziDzEWuGGHrqd4.jpg\n" +
                "딤섬,https://cdn.topdigital.com.au/news/photo/201906/img_7496_0.jpg\n" +
                "사천탕면,https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F244E184253D65A442B\n" +
                "잡탕밥,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "동파육,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "짬뽕밥,https://craftlog.com/m/i/6107302=s1280=h960\n" +
                "볶음,https://i.ytimg.com/vi/iPk145zzwws/maxresdefault.jpg\n" +
                "조개류,https://mono-kitchen.co.kr/shopimages/monob2c/mobile/0/3519450_represent\n" +
                "야채 요리,https://www.chungjungone.com/upload/knowhow/table/20181215_225148426_66698.jpg\n" +
                "마라두부,https://i.ytimg.com/vi/8Ei56Dj3z3g/maxresdefault.jpg\n" +
                "리조또,https://img-cf.kurly.com/shop/data/goodsview/20190510/gv10000049841_1.jpg\n" +
                "꼬치,https://d20aeo683mqd6t.cloudfront.net/ko/articles/title_images/000/039/313/medium/pixta_48321291_L_L.jpg?2019\n" +
                "짜장밥,https://recipe1.ezmember.co.kr/cache/recipe/2020/06/04/d96e1e81ecc8d86c922d486ec6eec4da1_m.jpg\n" +
                "짬뽕,짜장면,https://recipe1.ezmember.co.kr/cache/recipe/2020/06/04/d96e1e81ecc8d86c922d486ec6eec4da1_m.jpg\n" +
                "오코노미야키,https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/9f010965c00c8edd4439e0d1e359c7fe.jpg\n" +
                "라멘,https://post-phinf.pstatic.net/MjAxODA0MTdfMjAx/MDAxNTIzOTQ0MTA1MTU2.I62RlGSw9bycOw5G5-wqW57w9Iq0gdpgkJaKszNBFxYg.C4qQPCCJBbxaXRBmy9aNsFq9l0glcLZ23Di0-F15hvkg.JPEG/shutterstock_723122572.jpg?type=w1200\n" +
                "회,https://cdn.onul-hoi.com/uploads/store_package/image/433/9d29a6_200520094545.jpg\n" +
                "갑각류 요리,https://img.etoday.co.kr/pto_db/2020/03/600/20200330152625_1442589_724_483.jpg\n" +
                "나베,https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/9f010965c00c8edd4439e0d1e359c7fe.jpg\n" +
                "육회,https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/20190804101604_photo1_509358843c07.jpg\n" +
                "치킨,https://rereco.co/wp-content/uploads/2018/02/1.jpg\n" +
                "규카츠,https://cdn.imweb.me/upload/S201612185855798c0088d/5c9dc98cb8668.jpg\n" +
                "타코야키,https://tourimage.interpark.com//Spot/200/17184/201604/6359564900198849081.jpg\n" +
                "덮밥,https://recipe1.ezmember.co.kr/cache/recipe/2019/01/05/8cf7a3c0e113de435fa189b1d3e6984c1.jpg\n" +
                "파스타,https://recipe1.ezmember.co.kr/cache/recipe/2019/03/03/fca1b1c8f05592f409b6fbe702e2112a1.jpg\n" +
                "버거,https://nimage.g-enews.com/phpwas/restmb_allidxmake.php?idx=5&simg=2017070609524404287a8b5e7c93c12114162187.jpg\n" +
                "스테이크,https://post-phinf.pstatic.net/MjAxOTExMTJfMTYg/MDAxNTczNTQ2NjYyMzgw.vBAdXx3WVbrgBHtzpzXrTKfVsbMPz4JmwgWXqbVCKckg.WQpJnlL5JIQQGSEtzr1MAVUZGlGMI09DuPhCgky5tYIg.PNG/%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88-%EB%8C%80%EB%B0%B1%EA%B3%BC-%EC%8A%A4%ED%85%8C%EC%9D%B4%ED%81%AC_post_sub_06.png?type=w1200\n" +
                "샐러드,https://ppss.kr/wp-content/uploads/2018/03/food-3171570_960_720.jpg\n" +
                "돼지 고기,https://post-phinf.pstatic.net/MjAyMDAzMDNfMTcg/MDAxNTgzMTkwNjA3ODQ5.kUXPHqGJ2xPDSu_3FiEoFC3kY9QyQ_g9CziCGrFSDuEg.LpCfOTbc5qth9d-GKzGv9jwj2VKhcqmPHp5cp1KJYEsg.JPEG/IM_food02.jpg?type=w1200\n" +
                "스프,https://stimg.emart.com:448/upload/peacock/item/20180831_1317031_049.jpg\n" +
                "샌드위치,https://post-phinf.pstatic.net/MjAxODA3MTRfMTU4/MDAxNTMxNDk3MzIzMzAz.FY7cK-RNcK13YbqA-lgvdVXliShr9x4kgp98o8_urBcg.tc8RPtiScclX4oPfP8AaE8k9tpE0FTSl7XVKPoCWRWgg.PNG/%EC%BA%A1%EC%B3%90%EC%9A%A9_%EB%B8%8C%EB%A1%B1%EB%B6%80%EB%B6%80%EC%9D%98_%EC%98%81%EC%83%81%EB%A0%88%EC%8B%9C%ED%94%BC_%EC%83%8C%EB%93%9C%EC%9C%84%EC%B9%98%EB%A7%8C%EB%93%A4%EA%B8%B0.mp4_000140075.png?type=w1200\n" +
                "잠발라야,https://t1.daumcdn.net/cfile/tistory/99B170335C3F573E31\n" +
                "바베큐,https://plaeat.com/upfiles/new_product/S0130541/1000x584_7b57fa2a0433b17e77c1337b3ca6980e_1561348310_7538.jpg\n" +
                "카레,https://previews.123rf.com/images/yatomo/yatomo1307/yatomo130700085/21131812-%EC%9D%B8%EB%8F%84-%EC%B9%B4%EB%A0%88.jpg\n" +
                "타코,https://post-phinf.pstatic.net/MjAxODEyMTlfNDgg/MDAxNTQ1MTg0MzUzODA3.ya0LaPX7eIa3EZG4yJ2kk799kErRuXGYvc-GYIddijAg.aEDZfb2IE_6a58jGTvaq7VtbfVUbGr6bSf3C7e-t6Xsg.PNG/2018-12-18_17%3B20%3B58.PNG?type=w1200\n" +
                "동남아 요리,https://pds.joins.com//news/component/htmlphoto_mmdata/201807/31/ce6d8fec-2253-4719-8699-9d2a0aa49fb2.jpg\n" +
                "인도 요리,https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000458/img/basic/a0000458_main.jpg?20170429125843&q=80&rw=750&rh=536\n" +
                "커리,https://cdn.crowdpic.net/list-thumb/thumb_l_6BF0A013342707D2F01BEAFBA2502ABF.jpg\n" +
                "국수,https://www.palnews.co.kr/news/photo/201712/91750_24575_1653.jpg\n" +
                "국밥,https://img.insight.co.kr/static/2019/08/12/700/y8jzfe6100x3yvgq39el.jpg\n" +
                "멕시칸 요리,https://img1.mashed.com/img/gallery/mexican-foods-you-need-to-try-before-you-die/intro-1585677665.jpg\n" +
                "소고기,https://cdn.crowdpic.net/list-thumb/thumb_l_6B4A45748765A3EBAC01F210C1A05346.jpg\n" +
                "불고기,https://recipe1.ezmember.co.kr/cache/recipe/2016/12/30/df939769792632a48a0eba8bc895e8601.jpg\n" +
                "곱창,https://img1.tmon.kr/cdn3/deals/2019/11/27/2194714050/original_2194714050_front_09b9e_1574838649production.jpg\n" +
                "대창,https://image.auction.co.kr/itemimage/1a/b9/5e/1ab95ecf46.jpg\n" +
                "막창,https://img-cf.kurly.com/shop/data/goodsview/20180426/gv10000022154_1.jpg\n" +
                "쭈꾸미,https://foodyap.co.kr/shopimages/goldplate1/049003000012.jpg?1579764539\n" +
                "오징어,https://shop.biumfood.com/upload/1535360125_2941800.jpg\n" +
                "닭갈비,https://img2.tmon.kr/cdn3/deals/2019/08/26/2370430098/2370430098_front_iJ60LlMHYy.jpg\n";

        StringTokenizer tk = new StringTokenizer(file, "\n");
        while (tk.hasMoreTokens() ) {
            String token = tk.nextToken();
            StringTokenizer tk2 = new StringTokenizer(token, ",");
            Log.d("src countToken",Integer.toString(tk2.countTokens()));
            if(tk2.countTokens()>2){
                map.put(tk2.nextToken()+", "+tk2.nextToken(), tk2.nextToken());
            }else {
                map.put(tk2.nextToken(), tk2.nextToken());
            }
        }

        mKeys = map.keySet().toArray(new String[map.size()]);
    }
    public String getFoodSrc(String foodName){
        return map.get(foodName) == null? "":map.get(foodName);

        //이미지 테스트 코드
        //Log.d("src","foodName:" + mKeys[cn*9 + cnt] + "," + (cn*9+cnt) + "\n" + "foodSrc:"+map.get(mKeys[cn*9 + cnt]));
        //return map.get(mKeys[cn*9 + cnt++]);
    }

}
