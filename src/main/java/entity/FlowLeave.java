package entity;

import java.util.Boolean;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 请假流程(FlowLeave)表实体类
 *
 * @author makejava
 * @since 2024-08-10 23:36:32
 */
@TableName("redpig_flow_leave")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowLeave implements Serializable {
    //主键id
    private Long id;
    //删除状态：0、已删除 1、未删除
    private Integer deletestatus;
    //创建时间
    private Date createtime;
    //更新时间
    private Date updatetime;
    //请假申请人ID
    private Long userId;
    //请假理由
    private String leaveReason;
    //请假起始时间
    private Date starttime;
    //请假结束时间
    private Date endtime;
    //流程实例ID
    private String processinstanceId;
    //流程定义ID
    private String processdefinitionId;
    //请假是否同意
    private Boolean agree;
    //创建者
    private String createBy;
    //更新者
    private String updateBy;

}

