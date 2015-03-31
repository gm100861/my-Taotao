package com.taotao.manage.vo;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SearchItem {

    /**
     * 商品id，同时也是商品编号
     */
    @Field("id")
    private Long id;

    /**
     * 商品标题
     */
    // 不加也可以，使用@Transient忽略字段
    @Field("title")
    private String title;

    /**
     * 商品卖点
     */
    @Field("sellPoint")
    private String sellPoint;

    /**
     * 商品价格，单位为：分
     */
    @Field("price")
    private Long price;

    /**
     * 所属类目，叶子类目
     */
    @Field("cid")
    private Long cid;

    /**
     * 库存数量
     */
    private Integer num;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    @Field("status")
    private Integer status;

    /**
     * 商品条形码
     */
    private String barcode;

    /**
     * 商品图片
     */
    @Field("image")
    private String image;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    @JsonIgnore
    public String[] getImages(){
        if(getImage() == null){
            return null;
        }
        return getImage().split(",");
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price
                + ", cid=" + cid + ", num=" + num + ", status=" + status + ", barcode=" + barcode
                + ", image=" + image + ", created=" + created + ", updated=" + updated + "]";
    }

}
