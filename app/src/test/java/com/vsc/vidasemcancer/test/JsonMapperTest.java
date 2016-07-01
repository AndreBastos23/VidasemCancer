package com.vsc.vidasemcancer.test;


import android.util.Log;

import com.vsc.vidasemcancer.Mappers.PostMapper;
import com.vsc.vidasemcancer.entities.Post;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.mockito.Mockito.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(android.util.Log.class)
public class JsonMapperTest extends TestCase {
    List<Post> postList;
    private File f;
    private PostMapper postMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PowerMockito.mockStatic(Log.class);
        Mockito.when(Log.i(anyString(), anyString())).thenReturn(1);

        postMapper = new PostMapper();
        f = new File("D:\\Users\\Eduardo\\AndroidStudioProjects\\vsc\\app\\src\\test\\java\\com\\vsc\\vidasemcancer\\test\\posts.json");
        FileInputStream inputStream = new FileInputStream(f);

        String response = "";
        int content;
        while ((content = inputStream.read()) != -1) {
            response += (char) content;
        }
        inputStream.close();

        postList = postMapper.mapPosts(response);

    }


    @Test
    public void testPostMapper() {
        Assert.assertNotSame("Number of posts", 0, postList.size());
    }

    @Test
    public void testCorrectOrder() {
        Assert.assertTrue("Post Order", postList.get(0).getTitle().getRendered().contains("Luta"));
    }
}
