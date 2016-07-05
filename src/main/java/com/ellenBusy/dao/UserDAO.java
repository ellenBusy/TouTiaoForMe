package com.ellenBusy.dao;

import com.ellenBusy.model.User;
import com.ellenBusy.model.News;
import com.ellenBusy.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by rainday on 16/6/30.
 */
@Mapper
public interface UserDAO {

    String TABLE_NAME = "user";

    String INSERT_FIELDS = " name, password, salt, head_url ";//加空格,这样把属性写出来改起来方便

    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    @Insert({
            "insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") Values (#{name}, #{password}, #{salt}, #{headUrl})"
    })
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
