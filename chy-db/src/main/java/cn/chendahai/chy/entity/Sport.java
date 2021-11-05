package cn.chendahai.chy.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String avatar;

    private String betradarId;

    private Date cts;

    private String description;

    private Date uts;

    private Integer status;

    private Integer seq;

}
