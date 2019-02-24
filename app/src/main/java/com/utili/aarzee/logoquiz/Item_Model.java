package com.utili.aarzee.logoquiz;

/**
 * Created by ram on 2/24/2017.
 */

public class Item_Model {
    Integer id = 0;
    String item_name = "";
    String item_id = "";
    String grp_id ="";
    String result = "";
    Integer hang_no = 0;
    String option = "";
    String correct_try = "";
    String hint1 = "";
    String hint2 = "";
    String wiki_link = "";
    String reference_try = "";
    Integer countSuccess = 0;
    Integer countFail = 0;
    Integer countHanged = 0;
    Integer countTotal = 0;
    String proper_name = "";
    Integer image2_status = 0;
    Integer image3_status = 0;
    Integer image4_status = 0;
    String reference_options = "";
    String item_name_filtered = "";


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(String grp_id) {
        this.grp_id = grp_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getHang_no() {
        return hang_no;
    }

    public void setHang_no(Integer hang_no) {
        this.hang_no = hang_no;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCorrect_try() {
        return correct_try;
    }

    public void setCorrect_try(String correct_try) {
        this.correct_try = correct_try;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getWiki_link() {
        return wiki_link;
    }

    public void setWiki_link(String wiki_link) {
        this.wiki_link = wiki_link;
    }

    public String getReference_try() {
        return reference_try;
    }

    public void setReference_try(String reference_try) {
        this.reference_try = reference_try;
    }

    public Integer getCountSuccess() {
        return countSuccess;
    }

    public void setCountSuccess(Integer countSuccess) {
        this.countSuccess = countSuccess;
    }

    public Integer getCountFail() {
        return countFail;
    }

    public void setCountFail(Integer countFail) {
        this.countFail = countFail;
    }

    public Integer getCountHanged() {
        return countHanged;
    }

    public void setCountHanged(Integer countHanged) {
        this.countHanged = countHanged;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public String getProper_name() {
        return proper_name;
    }

    public void setProper_name(String proper_name) {
        this.proper_name = proper_name;
    }

    public Integer getImage2_status() {
        return image2_status;
    }

    public void setImage2_status(Integer image2_status) {
        this.image2_status = image2_status;
    }

    public Integer getImage3_status() {
        return image3_status;
    }

    public void setImage3_status(Integer image3_status) {
        this.image3_status = image3_status;
    }

    public Integer getImage4_status() {
        return image4_status;
    }

    public void setImage4_status(Integer image4_status) {
        this.image4_status = image4_status;
    }

    public String getReference_options() {
        return reference_options;
    }

    public void setReference_options(String reference_options) {
        this.reference_options = reference_options;
    }

    public String getItem_name_filtered() {
        return item_name_filtered;
    }

    public void setItem_name_filtered(String item_name_filtered) {
        this.item_name_filtered = item_name_filtered;
    }
}
