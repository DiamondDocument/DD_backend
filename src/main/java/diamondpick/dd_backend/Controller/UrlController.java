package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Service.LocalFileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

@RestController
public class UrlController {
    LocalFileService localFileService;

    @GetMapping(value="/api/url/{location}")
    public @ResponseBody
    void url(@PathVariable String location, HttpServletResponse response) {
        try{
            response.reset();
            response.setContentType(localFileService.getContentTypeByPath(location));
            response.getOutputStream().write(localFileService.getByLocation(location));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @GetMapping(path = "/api/document/download")
    public @ResponseBody void download(HttpServletResponse response)  {
        try{
            response.reset();
            response.setContentType(URLConnection.guessContentTypeFromName("abc.docx"));
            response.getOutputStream().write(localFileService.getDocx());
            response.flushBuffer();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
