package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.param.SearchAddressRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchControllerTest {
    @Autowired
    SearchController searchController;
    @Test
    void getTagsByNameLike() {
        CommonResult<List<Tag>> result = searchController.getTagsByNameLike("te");
        List<Tag> data = result.getData();
        assert data.size()==1;
        assert data.get(0).getName().equals("test");
    }

    @Test
    void getPostsByTag() {
        CommonResult<Map<String,Object>> result = searchController.getPostsByTag("lng",0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("result");
        assert list.size()==2;
        assert list.get(0).getContent().getContent().equals("i am going to join lng<br>");
    }

    @Test
    void getPostsByAuthor() {
        CommonResult<Map<String,Object>> result = searchController.getPostsByAuthor("user001",0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("result");
        assert list.size()==1;
        assert list.get(0).getOwnerName().equals("user001");

    }

    @Test
    void getPostsByNameLike() {
        CommonResult<Map<String,Object>> result = searchController.getPostsByNameLike(1.0,1.0,0.5,0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("result");
        assert list.size()==1;
        assert (Math.pow(list.get(0).getLongitude()-1,2)+Math.pow(list.get(0).getLatitude()-1,2)<=Math.pow(0.5,2));
    }

    @Test
    void getPostByAddress() {
        SearchAddressRequest searchAddressRequest = new SearchAddressRequest();
        searchAddressRequest.setCity("上海");
        searchAddressRequest.setPage(0);
        searchAddressRequest.setSize(10);
        CommonResult<Map<String,Object>> result = searchController.getPostByAddress(searchAddressRequest);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("result");
        assert list.size()==5;
        assert list.get(0).getCity().equals("上海市");
    }

    @Test
    void getTagByNameLike() {
        CommonResult<Map<String,Object>> result = searchController.getTagByNameLike("伪装,程序员修养",0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("result");
        assert list.size()==2;
        assert containTag(list.get(0).getTags(),"伪装");
        assert containTag(list.get(0).getTags(),"程序员修养");
    }

    private boolean containTag(Set<Tag> tagSet, String name){
        for (Tag tag:tagSet){
            if (tag.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}