package cloudcomputing.uni7.cloudapi.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Controller
public class TextractController {

    @GetMapping(value = "/home")
    public String list() {


        // The S3 bucket and document
        String document = "image.png";
        String bucket = "bucketname";

        AWSCredentials credentials =
                new BasicAWSCredentials("XXX", "XXX");

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                "https://textract.us-east-1.amazonaws.com", "us-east-1");
        AmazonTextract client = AmazonTextractClientBuilder.standard()
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();


        DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                .withDocument(new Document().
                        withS3Object(new S3Object().withName(document).withBucket(bucket)));

        DetectDocumentTextResult result = client.detectDocumentText(request);

        List<Block> blocks = result.getBlocks();

        String cpf = "";
        Integer index = 0;
        Integer cpfIndex = -1;
        for (Block block : blocks) {

            if (index.compareTo(cpfIndex) == 0) {
                cpf = block.getText();
                break;
            }

            if (block.getText() != null && "CPF".equalsIgnoreCase(block.getText())) {
                cpfIndex = index + 2;
            }

            index++;

        }

        System.out.println(cpf);

        return "home/home";
    }

}
