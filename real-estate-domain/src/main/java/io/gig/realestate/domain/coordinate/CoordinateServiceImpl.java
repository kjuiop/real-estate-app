package io.gig.realestate.domain.coordinate;

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


    @Override
    @Transactional
    public void readJsonFile(MultipartFile file) {

        List<JSONObject> data = new ArrayList<>();
        List<String> fails = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // JSONTokener를 사용하여 라인에서 JSON 파싱
                    JSONTokener tokener = new JSONTokener(line);
                    JSONObject geoJsonObject = new JSONObject(tokener);

                    // 여기에서 개별 라인의 GeoJSON 데이터에 대한 추가 작업 수행
                    data.add(geoJsonObject);
                } catch (Exception e) {
                    // JSON 파싱 에러가 발생하면 로그를 출력하고 계속 진행
                    e.printStackTrace();
                    fails.add(line);
                }
            }

            System.out.println("line : ");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.");
        }
    }
}
