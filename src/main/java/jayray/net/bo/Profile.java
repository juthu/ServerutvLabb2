package jayray.net.bo;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sirena on 2015-11-10.
 */

@NamedQueries({
        @NamedQuery(
                name = "findUserByUsernameContains",
                query = "from Profile p where (p.name like :search and p.name <> :exclude )"
        )
})
@Entity
@Table(name="profile")
public class Profile implements Serializable{
    private static final long serialVersionUID = 1L;

    private long u_id;
    private String name;
    private int age;
    @Column( columnDefinition = "boolean default true")
    private boolean isFemale = true;
    private User user;
    private String description;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsFemale() {
        return isFemale;
    }

    public void setIsFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="u_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString(){
        String sex="male";
        if(getIsFemale()){
            sex="female";
        }
        return name+", "+age+", "+description+", "+sex;
    }


}