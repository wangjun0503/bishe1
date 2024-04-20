<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章分类数据模型
const patientcategorys = ref([
    {
        "id": 3,
        "patientName": "已经完成编写",
        "patientSex": "my",

        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "patientName": "未完成编写",
        "patientSex": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },

])

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//文章列表数据模型
const patients = ref([
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },

])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    articleList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    articleList()
}


//回显文章分类
import { articleCategoryListService, articleListService,articleAddService } from '@/api/article.js'
const articleCategoryList = async () => {
    let result = await articleCategoryListService();

    patientcategorys.value = result.data;
    console.log(patientcategorys.value);
}
articleCategoryList();

//获取文章列表数据
const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await articleListService(params);

    //渲染视图
    total.value = result.data.total;
    patients.value = result.data.items;

    //处理数据,给数据模型扩展一个属性patientName,分类名称
    for (let i = 0; i < patients.value.length; i++) {
        let article = patients.value[i];
        for (let j = 0; j < patientcategorys.value.length; j++) {
            if (article.categoryId == patientcategorys.value[j].id) {
                article.patientName = patientcategorys.value[j].patientName;
            }
        }
    }
}


articleCategoryList()
articleList();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
})


//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result)=>{
    articleModel.value.coverImg = result.data;
    console.log(result.data);
}

//添加文章
import {ElMessage} from 'element-plus'
const addArticle = async (clickState)=>{
    //把发布状态赋值给数据模型
    articleModel.value.state = clickState;

    //调用接口
    let result = await articleAddService(articleModel.value);

    ElMessage.success(result.msg? result.msg:'添加成功');

    //让抽屉消失
    visibleDrawer.value = false;

    //刷新当前列表
    articleList()


}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>病人管理</span>
<!--                <div class="extra">-->
<!--                    <el-button type="primary" @click="visibleDrawer = true">添加文章</el-button>-->
<!--                </div>-->
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="是否诊断：">
                <el-select placeholder="请选择" v-model="categoryId">
                  <el-option label="已诊断" value="1"></el-option>
                  <el-option label="未诊断" value="0"></el-option>

                </el-select>
            </el-form-item>

            <el-form-item label="状态：">
                <el-select placeholder="请选择" v-model="state">
                    <el-option label="已出结果" value="1"></el-option>
                    <el-option label="未出结果" value="0"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">搜索</el-button>
                <el-button @click="categoryId = ''; state = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->

        <el-table :data="patients" style="width: 100%">
            <el-table-column label="病人姓名" width="400" prop="extraName"></el-table-column>
            <el-table-column label="分类" prop="patientName">

            </el-table-column>
            <el-table-column label="发表时间" prop="create_time"> </el-table-column>
            <el-table-column label="状态" prop="state">

            </el-table-column>
            <el-table-column  width="100"  type="expand">



                <template #default="{ row }">
                  <div class="block">
<!--                    'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'-->
                    <el-image v-if="row.user_ill_res== null" :src="row.user_ill_res"></el-image>
                    <el-image v-else src="'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'"></el-image>
                  </div>
                  <el-input
                      placeholder="请输入病人信息"
                      v-model="input"
                      :disabled="false">
                  </el-input>
                  <el-button type="primary">提交</el-button>



                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />


        <!-- 抽屉 -->
<!--        <el-drawer v-model="visibleDrawer" title="添加文章" direction="rtl" size="50%">-->
<!--            &lt;!&ndash; 添加文章表单 &ndash;&gt;-->
<!--            <el-form :model="articleModel" label-width="100px">-->
<!--                <el-form-item label="文章标题">-->
<!--                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="文章分类">-->
<!--                    <el-select placeholder="请选择" v-model="articleModel.categoryId">-->
<!--                        <el-option v-for="c in patientcategorys" :key="c.id" :label="c.patientName" :value="c.id">-->
<!--                        </el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="文章封面">-->

<!--                    &lt;!&ndash; -->
<!--                        auto-upload:设置是否自动上传-->
<!--                        action:设置服务器接口路径-->
<!--                        name:设置上传的文件字段名-->
<!--                        headers:设置上传的请求头-->
<!--                        on-success:设置上传成功的回调函数-->
<!--                     &ndash;&gt;-->
<!--                   -->
<!--                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"-->
<!--                    action="/api/upload"-->
<!--                    name="file"-->
<!--                    :headers="{'Authorization':tokenStore.token}"-->
<!--                    :on-success="uploadSuccess"-->
<!--                    >-->
<!--                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />-->
<!--                        <el-icon v-else class="avatar-uploader-icon">-->
<!--                            <Plus />-->
<!--                        </el-icon>-->
<!--                    </el-upload>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="文章内容">-->
<!--                    <div class="editor">-->
<!--                        <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html">-->
<!--                        </quill-editor>-->
<!--                    </div>-->
<!--                </el-form-item>-->
<!--                <el-form-item>-->
<!--                    <el-button type="primary" @click="addArticle('已出结果')">发布</el-button>-->
<!--                    <el-button type="info" @click="addArticle('未出结果')">草稿</el-button>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--        </el-drawer>-->
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>