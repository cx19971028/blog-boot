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
public class MsSysUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id")
      private String id;

      /**
     * 账号
     */
      private String account;

      /**
     * 是否管理员
     */
      private Integer admin;

      /**
     * 头像
     */
      private String avatar;

      /**
     * 注册时间
     */
      private Long createDate;

      /**
     * 是否删除
     */
      private Integer deleted;

      /**
     * 邮箱
     */
      private String email;

      /**
     * 最后登录时间
     */
      private Long lastLogin;

      /**
     * 手机号
     */
      private String mobilePhoneNumber;

      /**
     * 昵称
     */
      private String nickname;

      /**
     * 密码
     */
      private String password;

      /**
     * 加密盐
     */
      private String salt;

      /**
     * 状态
     */
      private String status;


}
