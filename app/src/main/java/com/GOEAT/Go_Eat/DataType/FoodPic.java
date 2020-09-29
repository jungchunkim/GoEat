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

    public FoodPic() {
        String file = "갈비찜,https://recipe1.ezmember.co.kr/cache/recipe/2016/12/13/0f12719bc6d713fd727d5f1cd65ad6141.jpg\n" +
                "우삼겹,https://3.bp.blogspot.com/-2JjhR7h1O1g/VuVl8XnG-fI/AAAAAAAAZYU/TD6BxwPaed4r6pFFHeE0KlCfZ87GKz6gA/s640/DSC03514.JPG\n" +
                "소고기전골,https://i.ytimg.com/vi/CeGfUvKBLKs/maxresdefault.jpg\n" +
                "제육볶음,https://recipe1.ezmember.co.kr/cache/recipe/2018/02/12/5bde81aa7309397e596846010fb5e6f71.jpg\n" +
                "삼겹살,https://i.pinimg.com/originals/c4/c5/d5/c4c5d5d428634b5ddf1aabd9f98faf60.jpg\n" +
                "메밀전,https://blog.kakaocdn.net/dn/pzvik/btqzEDDwKQA/mk8KpyMdyadfkCYuFbk6ek/img.png\n" +
                "수제비,https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRzKhI2Gneee4bxPvM30hh5y5iQtehElLElbw&usqp=CAU\n" +
                "갈비만두,https://m.hanmandushop.com/web/product/big/201804/101_shop1_234554.jpg\n" +
                "비빔밥,https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRj3X9tMAg-chptWHNFvGwiY4buQFnYZI4HOw&usqp=CAU\n" +
                "육개장,https://www.travie.com/news/photo/first/201607/img_19196_1.jpg\n" +
                "칼국수,\"https://mp-seoul-image-production-s3.mangoplate.com/1623/322577_1564500533408_11790?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80\"\n" +
                "수육,https://craftlog.com/m/i/6244757=s1280=h960\n" +
                "육회,https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/20190804101604_photo1_509358843c07.jpg\n" +
                "순두부,https://img.ssfshop.com/cmd/LB_500x660/src/https://img.ssfshop.com/goods/ORBR/20/02/12/GPAQ20021262406_0_ORGINL_20200423115609972.jpg\n" +
                "덮밥,https://recipe1.ezmember.co.kr/cache/recipe/2019/01/05/8cf7a3c0e113de435fa189b1d3e6984c1.jpg\n" +
                "백숙,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/04/d390442f043748c02a90beeabefc5dae1.jpg\n" +
                "매운 낙지,https://recipe1.ezmember.co.kr/cache/recipe/2017/09/15/870609fd6e486df1f8f3e09f322aedeb1.jpg\n" +
                "매운 쭈꾸미,https://recipe1.ezmember.co.kr/cache/recipe/2017/06/21/b30caebf25bda7193fb1c345f082f8d41.jpg\n" +
                "전,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/11/7263705e8504004e76c8032753a104181.jpg\n" +
                "생선조림,https://recipe1.ezmember.co.kr/cache/recipe/2018/12/10/b4ab2144a8b58f8a5d055c37cf7563941.jpg\n" +
                "생선 구이,https://lh3.googleusercontent.com/proxy/tMHIWdO8oJNA_i1kdWaoWli0mpwIaBCn433Fk1UFZ90XhEPdOs2dA_uvQbRzvtYyb6FFc23YYDOnJGKSrB66UIrAH5DzcQHM8InLu2aL5X3FXlG9T6RBhcQazv1I7oM-bCrHPlZb\n" +
                "김치찜,https://recipe1.ezmember.co.kr/cache/recipe/2017/11/21/0a09214720ddf173d39103262f06ebf11.jpg\n" +
                "대구탕,https://recipe1.ezmember.co.kr/cache/recipe/2019/06/17/9618f566128cca0bb933c4b727d378f91.jpg\n" +
                "떡갈비,https://craftlog.com/m/i/5784724=s1280=h960\n" +
                "볶음밥,https://recipe1.ezmember.co.kr/cache/recipe/2015/08/27/932b0eac49b0f341ee9b91553d84d9b91.jpg\n" +
                "주먹밥,https://i.ytimg.com/vi/Gmyh4cgAlK8/maxresdefault.jpg\n" +
                "초밥,https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000881/img/basic/a0000881_main.jpg?20200630185341&q=80&rw=750&rh=536\n" +
                "튀김,https://craftlog.com/m/i/5842264=s1280=h960\n" +
                "돈까스,https://th3.tmon.kr/thumbs/image/6ea/082/d57/2906e06a0_700x700_95_FIT.jpg\n" +
                "떡볶이,https://img3.tmon.kr/cdn3/deals/2019/07/23/2280944206/front_e7b5f_nfan1.jpg\n" +
                "라볶이,https://recipe1.ezmember.co.kr/cache/recipe/2018/04/12/f4b3e8bfca87fe71f9b6764b5703e7181.jpg\n" +
                "토스트,https://t1.daumcdn.net/cfile/tistory/9998B74F5A529F061C\n" +
                "베이글,https://lh3.googleusercontent.com/proxy/4apf7RvyMtHoO2PsxX5oEmnfTJdwYx9RIlAP55buFI2oIKgGfVe4tMhyuMdX6jjNhtbHtmfQX2jNU5waMXw4sq3LrTTcSmxjjpwOFOzTHzXNEdvId-FZku4LMg0hMhPyh4xaiYpFHdfen5sScNFknUdbFpfXR07C8Obggb9i1eOxBSGR1b7gYDw_KWmE2ryLr54XSM2c1JZtv0cqLVirG0B3kRsvWrcXjTCAU8YeVx1bWOQ8GK8uGldXxaUbru4sTjmDBXVKCRgweJBN1fgxdNW2jVvsUWUakwCCJrHMWUZGsVcvcezSgmcnUUQ2\n" +
                "피자,https://img.huffingtonpost.com/asset/5bed0be3220000bc04de3cbc.jpeg?cache=s6tbBWRVTk&ops=crop_495_410_4396_2730%2Cscalefit_720_noupscale\n" +
                "우동,https://recipe1.ezmember.co.kr/cache/recipe/2015/06/08/0fd69039751d2e9d6649ae63bca07cd2.jpg\n" +
                "쫄면,https://www.nadrifood.co.kr/wp-content/uploads/2017/12/goods_01.jpg\n" +
                "냉면,https://thumbnail.10x10.co.kr/webimage/image/basic600/232/B002324904.jpg?cmd=thumb&w=500&h=500&fit=true&ws=false\n" +
                "소바,https://recipe1.ezmember.co.kr/cache/recipe/2017/07/25/2e956f8217b643fef8b94370dfd6ceda1.jpg\n" +
                "덮밥,https://www.newiki.net/w/images/thumb/d/dd/Jeyukdeopbap.jpg/450px-Jeyukdeopbap.jpg\n" +
                "찌개,https://recipe1.ezmember.co.kr/cache/recipe/2017/04/26/ddd495fd432955701068e1a21a0d33211.jpg\n" +
                "돈까스,https://th3.tmon.kr/thumbs/image/6ea/082/d57/2906e06a0_700x700_95_FIT.jpg\n" +
                "김밥,https://recipe1.ezmember.co.kr/cache/recipe/2016/06/29/e7401296033ab8e4297cd53d71e1bba91.jpg\n" +
                "닭볶음탕,https://recipe1.ezmember.co.kr/cache/recipe/2016/11/23/a6446c45269c63b1c5a1e7d2e7ccd2cf1.jpg\n" +
                "닭발,https://cdn.imweb.me/thumbnail/20180908/5b92b390e58ea.jpg\n" +
                "짜장면,https://recipe1.ezmember.co.kr/cache/recipe/2020/06/04/d96e1e81ecc8d86c922d486ec6eec4da1_m.jpg\n" +
                "짬뽕,https://craftlog.com/m/i/6107302=s1280=h960\n" +
                "기타 중식,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "탕수육,https://t1.daumcdn.net/cfile/blog/246CD65056DA502929\n" +
                "잡채,https://t1.daumcdn.net/cfile/tistory/2329B43F550456482C\n" +
                "마라탕,https://img1.tmon.kr/cdn3/deals/2019/05/24/2099407062/original_2099407062_front_935dc_1558688230production.jpg\n" +
                "마라샹궈,https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2mg6/image/9d8TOY3Yp6nCKziDzEWuGGHrqd4.jpg\n" +
                "덮밥,https://recipe1.ezmember.co.kr/cache/recipe/2015/07/16/e90d4aa496657c666f2821977e3c721e1.jpg\n" +
                "만두,https://lh3.googleusercontent.com/proxy/78qdLMjCIFK0oW9dc7mWR-Gl32WWYC8fi4RkEDAVo_6AB_Qq73SuI-406XmKBHeHmqn7_v6GItqrNYIHao_a3lNusaf7BHW-6vWgGKdXIA54tNlxEbIZMx-JtRJZziPObPG47Zek-JpMCIJCaJn6c5QTXavPbaFFvJohAA\n" +
                "리조또,https://img-cf.kurly.com/shop/data/goodsview/20190510/gv10000049841_1.jpg\n" +
                "쌀국수,https://t1.daumcdn.net/liveboard/realfood/8f543966d8b7478e905ec2e65433a596.JPG\n" +
                "기타일식,https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/9f010965c00c8edd4439e0d1e359c7fe.jpg\n" +
                "라멘,https://post-phinf.pstatic.net/MjAxODA0MTdfMjAx/MDAxNTIzOTQ0MTA1MTU2.I62RlGSw9bycOw5G5-wqW57w9Iq0gdpgkJaKszNBFxYg.C4qQPCCJBbxaXRBmy9aNsFq9l0glcLZ23Di0-F15hvkg.JPEG/shutterstock_723122572.jpg?type=w1200\n" +
                "랍스타,https://lh3.googleusercontent.com/proxy/6UJzfKA72VYckjJyXq9uk5rLwN3UfheTOXfdPH3vQuGvOvbCYEM1tzc6jvs7fJFjLTljUayez1bfwmzy-Nxza6xiLdwoH7WfrNi0PvT6dr3XbX35b8cHc-MayrBuYkZVo1o9yb0\n" +
                "회,https://cdn.onul-hoi.com/uploads/store_package/image/433/9d29a6_200520094545.jpg\n" +
                "일식덮밥,https://resources.matcha-jp.com/resize/720x2000/2017/01/20-14267.jpeg\n";

        StringTokenizer tk = new StringTokenizer(file, "\n");
        while (tk.hasMoreTokens() ) {
            String token = tk.nextToken();
            StringTokenizer tk2 = new StringTokenizer(token, ","); // 현재 분리단어는 " " 공백 스페이스로 나누어짐
            map.put(tk2.nextToken(),tk2.nextToken());
        }
    }
    public String getFoodSrc(String foodName){
        return map.get(foodName);
    }

}
