package com.njtech.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class MsArticle implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private String id;

      /**
     * 评论数量
     */
      private Integer commentCounts;

      /**
     * 创建时间
     */
      private Long createDate;

      /**
     * 简介
     */
      private String summary;

      /**
     * 标题
     */
      private String title;

      /**
     * 浏览数量
     */
      private Integer viewCounts;

      /**
     * 是否置顶
     */
      private Integer weight;

      /**
     * 作者id
     */
      private String authorId;

      /**
     * 内容id
     */
      private String bodyId;

      /**
     * 类别id
     */
      private Integer categoryId;


}
