package io.gig.realestate.domain.coordinate;

import io.gig.realestate.domain.realestate.vertex.Vertex;
import io.gig.realestate.domain.realestate.vertex.repository.VertexStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoordinateServiceImpl implements CoordinateService {

    private final VertexStoreRepository storeRepository;

    @Override
    public void readJsonFile(MultipartFile file) {

        List<JSONObject> data = new ArrayList<>();
        List<String> fails = new ArrayList<>();

        int i = 0;
        List<Vertex> vertices = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    i++;
                    if (i <= 500000) {
                        continue;
                    }
                    if (i > 800000) {
                        break;
                    }
                    Vertex vertex = Vertex.builder()
                            .jsonStr(line)
                            .build();
                    vertices.add(vertex);
                } catch (Exception e) {
                    // JSON 파싱 에러가 발생하면 로그를 출력하고 계속 진행
                    e.printStackTrace();
                    fails.add(line);
                }
            }
            storeRepository.saveAll(vertices);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.");
        }
    }
}
