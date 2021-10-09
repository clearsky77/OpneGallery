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
//                    startActivityForResult(intent, REQUEST_CODE) <- 이와 같이 되지 않아 아래처럼 변경...
                    startActivityForResult(intent, CONTEXT_INCLUDE_CODE)

                    Log.d("MainActivity", CONTEXT_INCLUDE_CODE.toString()) //

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
}