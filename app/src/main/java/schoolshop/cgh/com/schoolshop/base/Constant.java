package schoolshop.cgh.com.schoolshop.base;

import schoolshop.cgh.com.schoolshop.common.entity.Person;

/**
 * Created by HUI on 2017-04-13.
 * 常量类
 */

public class Constant {

    /**
     * 当前账号的person类
     */
    public static Person PERSON;

    /**
     * 当前商品类型
     */
    public static int Kind_Now = -1;

    /**
     * 商品分类:全部
     */
    public static final int Kind_All = 1;

    /**
     * 商品分类:书籍
     */
    public static final int Kind_Book = 2;

    /**
     * 商品分类:数码
     */
    public static final int Kind_Digit = 3;

    /**
     * 商品分类:服饰
     */
    public static final int Kind_Cloth = 4;

    /**
     * 商品分类:日用
     */
    public static final int Kind_Common = 5;

    /**
     * 商品分类:其他
     */
    public static final int Kind_Other = 6;

    /**
     * 发布中
     */
    public static final int TYPE_Selling = 1;

    /**
     * 已售出
     */
    public static final int TYPE_Selled = 2;

    /**
     * 已购买
     */
    public static final int TYPE_Buy = 3;

    /**
     * 收藏夹
     */
    public static final int Type_Fav = 5;

}
