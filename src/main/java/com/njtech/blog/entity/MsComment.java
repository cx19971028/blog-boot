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
public class MsComment implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id")
      private String id;

    private String content;

    private Long createDate;

    private String articleId;

    private String authorId;

    private String parentId;

    private String toUid;

    private Integer level;


}
