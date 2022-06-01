package diamondpick.dd_backend.yyh;

import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.TeamDealDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Old.zzy.Dao.CollectionDao;
import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.TeamService;
import diamondpick.dd_backend.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DdBackendApplicationTest {

    @Autowired
    UserDao userDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamDealDao teamDealDao;

    @Autowired
    UserService userService;
    @Test
    /*void insertUser(){
        userDao.insertUser("u123","yyh123","123456","good","123@qq.com",132);
    }*/
    /*void updateUser(){
        userDao.updateUser("u123","nickname","yyh6666");
    }*/
    /*void selectUser(){
        userDao.selectUser("u123");
    }*/
    /*void selectUserByEmail(){
        userDao.selectUserByEmail("123@qq.com");
    }*/
    /*void insertTeam(){
        teamDao.insertTeam("t123","diamond666","good","u123",132);
    }*/
    /*void selectMember(){
        userDao.selectMember("t123");
    }*/
    /*void selectCaptain(){
        userDao.selectCaption("t123");
    }*/
    /*void insertUser(){
        userDao.insertUser("u124","wyf123","123456","good","11223@qq.com",132);
    }*/
    /*void insertMember(){
        teamDao.insertMember("t123","u124");
    }*/
    /*void deleteMember(){
        teamDao.deleteMember("t123","u124");
    }*/
    /*void updateTeam(){
        teamDao.updateTeam("t123","intro","veryGood");
    }*/
    /*void selectTeam(){
        teamDao.selectTeam("t123");
    }*/
    /*void selectTeamByMember(){
        teamDao.selectTeamByMember("u123");
    }*/
    /*void selectTeamByCaptain(){
        teamDao.selectTeamByCaptain("u123");
    }*/
    /*void insertDeal(){
        teamDealDao.insertDeal("t123","u123",1);
    }*/
    /*void upsetStatus(){
        teamDealDao.updateStatus(1,1);
    }*/
    void selectDeal(){
        teamDealDao.selectDeal("t123","u124");
    }
    /*void deleteTeam(){
        teamDao.deleteTeam("t123");
    }*/
}