package com.njtech.blog.vo.param;

import lombok.Data;

@Data
public class PageParamVO {

    private Integer page = 1;
    private Integer pageSize = 10;
    private String categoryId;
    private String tagId;
    private String year;
    private String month;

    public String getMonth(){
        if(this.month != null && this.month.length()==1){
            return "0"+this.month;
        }
        return this.month;
    }
}
