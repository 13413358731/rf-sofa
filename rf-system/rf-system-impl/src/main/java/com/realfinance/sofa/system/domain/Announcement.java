package com.realfinance.sofa.system.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 公告
 */
/*@Entity
@Table(name = "SYSTEM_ANNOUNCEMENT",
        indexes = {

        })*/

// TODO: 2020/10/30 暂时未设计好
public class Announcement extends BaseEntity {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @Column
    @Lob
    private String content;

    /**
     * 开始时间
     */
    @Column(nullable = false)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Column(nullable = false)
    private LocalDateTime endTime;

    /**
     * 发送人
     */
    private Integer sender;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 消息分类
     */
    private String category;

    /**
     * 类型 指定用户或全体用户
     */
    private String type;

    /**
     * 发布状态（0未发布，1已发布，2已撤销，3删除）
     */
    private String status;

    private LocalDateTime sendTime;

    private LocalDateTime cancelTime;

    /**
     * 指定用户
     **/
    private String userIds;
    /**
     * 业务类型(email:邮件 bpm:流程)
     */
    private String busType;
    /**
     * 业务id
     */
    private String busId;
    /**
     * 打开方式 组件：component 路由：url
     */
    private String openType;
    /**
     * 组件/路由 地址
     */
    private String openPage;
    /**
     * 摘要
     */
    private String msgAbstract;
}
