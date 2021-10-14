package com.clearsky77.opnegallery

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQ_STORAGE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openGalleyBtn.setOnClickListener {
//          권한 승인 여부에 따른 규칙 정리.
            val pl = object : PermissionListener {

                // 권한이 허용 되었을 때. 실행한다.
                override fun onPermissionGranted() {
                    Toast.makeText(this@MainActivity, "권한 승인됨.", Toast.LENGTH_SHORT).show()
                    // 갤러리를 연다.
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    intent.type = "image/*"

//                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
//                    /* intent.type에서 설정한 종류의 데이터를
//                       MediaStore에서 불러와 목록으로 나영한 후 선택할 수 있는 앱이 실행 */

                    startActivityForResult(intent, REQ_STORAGE)
                }

                // 권한 거절 되었을 때.
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@MainActivity, "권한 거절됨.", Toast.LENGTH_SHORT).show()
                }
            }

            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) // 저장소 쓰기 권한
//                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) // 저장소 읽기 권한 <- 하나만 있어도 된다고 함
                .check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 만약 갤러리에서 돌아왔다면
        if(requestCode == REQ_STORAGE){
            if(resultCode == RESULT_OK){
                Log.d("onActivityResult", "갤러리에서 RESULT_OK 받았음")
            }
        }
    }
}