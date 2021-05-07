<template>
  <div v-loading.fullscreen.lock="fullscreenLoading" element-loading-text="正在生成简历PDF">
    <!-- 简历 -->
    <el-card id="printerContainer" :class="{'box-card': !edit, 'box-card-edit': edit}">
      <!-- 基本信息 -->
      <div
        class="freework-classic-item-top freework-classic-hover"
        v-if="resumeData.basicInfo"
        @click="displayItem('basicInfo')"
      >
        <div class="freework-classic-infotextbox">
          <p class="freework-classic-name">{{ resumeData.basicInfo.name }}</p>

          <div class="freework-classic-text">
            <span class="freework-classic-text">电话:</span>
            <span class="freework-classic-text">{{ resumeData.basicInfo.phone }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text freework-classic-hr-vert"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">邮箱:</span>
            <span class="freework-classic-text">{{ resumeData.basicInfo.email }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text freework-classic-hr-vert"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">所在地:</span>
            <span class="freework-classic-text">{{ resumeData.basicInfo.city }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text freework-classic-hr-vert"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">微信:</span>
            <span class="freework-classic-text">{{ resumeData.basicInfo.wechat }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text freework-classic-hr-vert"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">年龄:</span>
            <span class="freework-classic-text">{{ calAge() }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text freework-classic-hr-vert"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">性别:</span>
            <span class="freework-classic-text">{{ resumeData.basicInfo.sex === 0 ? '男' : '女' }}</span>
          </div>
          <el-divider direction="vertical" class="freework-classic-text"></el-divider>
          <div class="freework-classic-text">
            <span class="freework-classic-text">{{ resumeData.basicInfo.status}}</span>
          </div>
        </div>
        <div class="freework-classic-photo">
          <el-image :src="resumeData.basicInfo.photo" fit="fill"></el-image>
        </div>
      </div>
      <!-- 教育经历 -->
      <div class="freework-classic-item" v-if="resumeData.edus">
        <span class="freework-classic-title">{{ resumeData.edus.title }}</span>
        <el-button
          circle
          plain
          size="mini"
          type="primary"
          icon="el-icon-plus"
          class="noprint freework-classic-add-button"
          v-show="!edit"
          @click="addNode('edus')"
        ></el-button>
        <el-divider class="freework-classic-hr"></el-divider>
        <div
          v-for="(edu, index) in resumeData.edus.children"
          :key="index"
          class="freework-classic-single-line freework-classic-hover"
          @click="displayItem('edus', index)"
        >
          <div class="freework-classic-single-line">
            <span class="freework-classic-text freework-classic-school">{{edu.school}}</span>
            <div
              class="freework-classic-text"
              style="float: right;"
            >{{edu.timerange[0]}} - {{edu.timerange[1]}}</div>
          </div>
          <div class="freework-classic-single-line">
            <span class="freework-classic-text">{{edu.degree}} {{edu.faculty}} {{edu.major}}</span>
          </div>
          <div>
            <span v-html="edu.experiences" class="freework-classic-text"></span>
          </div>
        </div>
      </div>
      <!-- 专业技能 -->
      <div
        class="freework-classic-item freework-classic-hover"
        v-if="resumeData.skills"
        @click="displayItem('skills')"
      >
        <span class="freework-classic-title">{{ resumeData.skills.title }}</span>
        <el-divider class="freework-classic-hr"></el-divider>
        <div class="freework-classic-single-line">
          <span v-html="resumeData.skills.desc" class="freework-classic-text"></span>
        </div>
      </div>
      <!-- 实习经历 -->
      <div class="freework-classic-item" v-if="resumeData.internships">
        <span class="freework-classic-title">{{ resumeData.internships.title }}</span>
        <el-button
          circle
          plain
          size="mini"
          type="primary"
          icon="el-icon-plus"
          class="freework-classic-add-button noprint"
          v-show="!edit"
          @click="addNode('internships')"
        ></el-button>
        <el-divider class="freework-classic-hr"></el-divider>
        <div
          v-for="(internship, index) in resumeData.internships.children"
          :key="index"
          class="freework-classic-single-line freework-classic-hover"
          @click="displayItem('internships', index)"
        >
          <div class="freework-classic-single-line">
            <span class="freework-classic-text freework-classic-school">{{internship.companyName}}</span>
            <div
              class="freework-classic-text"
              style="float: right;"
            >{{internship.timerange[0]}} - {{internship.timerange[1]}}</div>
          </div>
          <div class="freework-classic-single-line">
            <span
              class="freework-classic-text"
            >{{internship.position}} {{internship.department}} {{internship.city}}</span>
          </div>
          <div>
            <span v-html="internship.desc" class="freework-classic-text"></span>
          </div>
        </div>
      </div>
      <!-- 开源项目及作品 -->
      <div class="freework-classic-item" v-if="resumeData.projects">
        <span class="freework-classic-title">{{ resumeData.projects.title }}</span>
        <el-button
          circle
          plain
          size="mini"
          type="primary"
          icon="el-icon-plus"
          class="freework-classic-add-button noprint"
          v-show="!edit"
          @click="addNode('projects')"
        ></el-button>
        <el-divider class="freework-classic-hr"></el-divider>
        <div
          v-for="(project, index) in resumeData.projects.children"
          :key="index"
          class="freework-classic-single-line freework-classic-hover"
          @click="displayItem('projects', index)"
        >
          <div class="freework-classic-single-line">
            <span class="freework-classic-text freework-classic-school">{{project.projectName}}</span>
            <div
              class="freework-classic-text"
              style="float: right;"
            >{{project.timerange[0]}} - {{project.timerange[1]}}</div>
          </div>
          <div class="freework-classic-single-line">
            <span class="freework-classic-text">{{project.duty}}</span>
          </div>
          <div>
            <span v-html="project.desc" class="freework-classic-text"></span>
          </div>
        </div>
      </div>
      <!-- 个人总结 -->
      <div
        class="freework-classic-item freework-classic-hover"
        v-if="resumeData.summaries"
        @click="displayItem('summaries')"
      >
        <span class="freework-classic-title">{{ resumeData.summaries.title }}</span>
        <el-divider class="freework-classic-hr"></el-divider>
        <div class="freework-classic-single-line">
          <span v-html="resumeData.summaries.desc" class="freework-classic-text"></span>
        </div>
      </div>
      <!-- 社团和组织经历 -->
      <div class="freework-classic-item" v-if="resumeData.communities">
        <span class="freework-classic-title">{{ resumeData.communities.title }}</span>
        <el-button
          circle
          plain
          size="mini"
          type="primary"
          icon="el-icon-plus"
          class="freework-classic-add-button noprint"
          v-show="!edit"
          @click="addNode('communities')"
        ></el-button>
        <el-divider class="freework-classic-hr"></el-divider>
        <div
          v-for="(community, index) in resumeData.communities.children"
          :key="index"
          class="freework-classic-single-line freework-classic-hover"
          @click="displayItem('communities', index)"
        >
          <div class="freework-classic-single-line">
            <span class="freework-classic-text freework-classic-school">{{community.activityName}}</span>
            <div
              class="freework-classic-text"
              style="float: right;"
            >{{community.timerange[0]}} - {{community.timerange[1]}}</div>
          </div>
          <div class="freework-classic-single-line">
            <span class="freework-classic-text">{{community.duty}}</span>
          </div>
          <div>
            <span v-html="community.desc" class="freework-classic-text"></span>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 编辑框 -->
    <!-- basicInfo -->
    <el-card class="item-edit-css" v-show="basicInfoEdit">
      <el-form ref="resumeData.basicInfo" :model="resumeData.basicInfo" label-width="80px">
        <el-form-item label="照片">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9003/upload/image"
            :show-file-list="false"
            :on-success="uploadPhotoSuccess"
            :before-upload="beforeUpload"
          >
            <img v-if="resumeData.basicInfo.photo" :src="resumeData.basicInfo.photo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="resumeData.basicInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="resumeData.basicInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="resumeData.basicInfo.email"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-cascader v-model="formattedAddress" :options="address" @change="handleChangeAddress"></el-cascader>
        </el-form-item>
        <el-form-item label="微信">
          <el-input v-model="resumeData.basicInfo.wechat"></el-input>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker
            type="date"
            placeholder="出生日期"
            v-model="resumeData.basicInfo.birthday"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="resumeData.basicInfo.sex" clearable placeholder="请选择">
            <el-option
              v-for="item in sexes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前状态">
          <el-input v-model="resumeData.basicInfo.status" placeholder="xx大学应届生 / xx公司在职" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('basicInfo')">保存</el-button>
          <!-- <el-button @click="quitItemEdit('basicInfo')">取消</el-button> -->
        </el-form-item>
      </el-form>
    </el-card>
    <!-- edus -->
    <el-card class="item-edit-css" v-show="edusEdit">
      <el-form
        ref="resumeData.edus.children[curEduIdx]"
        :model="resumeData.edus.children[curEduIdx]"
        label-width="80px"
      >
        <el-form-item label="学校">
          <el-input v-model="resumeData.edus.children[curEduIdx].school"></el-input>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="resumeData.edus.children[curEduIdx].faculty"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="resumeData.edus.children[curEduIdx].major"></el-input>
        </el-form-item>
        <el-form-item label="学历">
          <el-input v-model="resumeData.edus.children[curEduIdx].degree"></el-input>
        </el-form-item>
        <el-form-item label="在读时间">
          <el-date-picker
            v-model="resumeData.edus.children[curEduIdx].timerange"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy/MM"
            value-format="yyyy/MM"
            :clearable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="经历描述">
          <div>
            <tinymce v-model="resumeData.edus.children[curEduIdx].experiences" :height="300" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('edus')">保存</el-button>
          <!-- <el-button @click="quitItemEdit('edus')">取消</el-button> -->
        </el-form-item>
      </el-form>
    </el-card>
    <!-- skills -->
    <el-card class="item-edit-css" v-show="skillsEdit">
      <el-form ref="resumeData.skills" :model="resumeData.skills" label-width="80px">
        <el-form-item label="专业技能">
          <div>
            <tinymce v-model="resumeData.skills.desc" :height="300" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('skills')">保存</el-button>
          <!-- <el-button @click="quitItemEdit('skills')">取消</el-button> -->
        </el-form-item>
      </el-form>
    </el-card>
    <!-- internships -->
    <el-card class="item-edit-css" v-show="internshipsEdit">
      <el-form
        ref="resumeData.internships.children[curInternshipIdx]"
        :model="resumeData.internships.children[curInternshipIdx]"
        label-width="80px"
      >
        <el-form-item label="公司名称">
          <el-input v-model="resumeData.internships.children[curInternshipIdx].companyName"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="resumeData.internships.children[curInternshipIdx].position"></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <el-input v-model="resumeData.internships.children[curInternshipIdx].department"></el-input>
        </el-form-item>
        <el-form-item label="工作时间">
          <el-date-picker
            v-model="resumeData.internships.children[curInternshipIdx].timerange"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy/MM"
            value-format="yyyy/MM"
            :clearable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="经历描述">
          <div>
            <tinymce
              v-model="resumeData.internships.children[curInternshipIdx].desc"
              :height="300"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('internships')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- projects -->
    <el-card class="item-edit-css" v-show="projectsEdit">
      <el-form
        ref="resumeData.projects.children[curProjectIdx]"
        :model="resumeData.projects.children[curProjectIdx]"
        label-width="80px"
      >
        <el-form-item label="项目名称">
          <el-input v-model="resumeData.projects.children[curProjectIdx].projectName"></el-input>
        </el-form-item>
        <el-form-item label="职责">
          <el-input v-model="resumeData.projects.children[curProjectIdx].duty"></el-input>
        </el-form-item>
        <el-form-item label="项目时间">
          <el-date-picker
            v-model="resumeData.projects.children[curProjectIdx].timerange"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy/MM"
            value-format="yyyy/MM"
            :clearable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="项目描述">
          <div>
            <tinymce v-model="resumeData.projects.children[curProjectIdx].desc" :height="300" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('projects')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- summaries -->
    <el-card class="item-edit-css" v-show="summariesEdit">
      <el-form ref="resumeData.summaries" :model="resumeData.summaries" label-width="80px">
        <el-form-item label="个人总结">
          <div>
            <tinymce v-model="resumeData.summaries.desc" :height="300" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('summaries')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- communities -->
    <el-card class="item-edit-css" v-show="communitiesEdit">
      <el-form
        ref="resumeData.communities.children[curCommunityIdx]"
        :model="resumeData.communities.children[curCommunityIdx]"
        label-width="80px"
      >
        <el-form-item label="活动名称">
          <el-input v-model="resumeData.communities.children[curCommunityIdx].activityName"></el-input>
        </el-form-item>
        <el-form-item label="职责">
          <el-input v-model="resumeData.communities.children[curCommunityIdx].duty"></el-input>
        </el-form-item>
        <el-form-item label="项目时间">
          <el-date-picker
            v-model="resumeData.communities.children[curCommunityIdx].timerange"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy/MM"
            value-format="yyyy/MM"
            :clearable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="项目描述">
          <div>
            <tinymce v-model="resumeData.communities.children[curCommunityIdx].desc" :height="300" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveItem('communities')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- 打印 -->
    <el-button icon="el-icon-printer" circle @click="downloadPDF" class="download-css noprint"></el-button>
  </div>
</template>

<script>
import { calYearSub } from "@/utils/dateutil";
import { provinceAndCityData } from "element-china-area-data";
import { addToStr, strToAdd } from "@/utils/address";
import Tinymce from "@/components/Tinymce";

export default {
  components: { Tinymce },
  data() {
    return {
      article: "",
      fullscreenLoading: false,
      edit: false,
      resumeModel: ["basicInfo", "edus", "skills", "internships"],
      resumeData: {
        title: "温嘉伟简历1",
        basicInfo: {
          title: "基本信息",
          name: "温嘉伟",
          photo:
            "http://image.wjw.today/group1/M00/00/01/cx3BSmCQO86AGIUCAAJ5IFnKu4w745.png",
          phone: "13076271026",
          email: "wjw990518@163.com",
          city: "广东省-韶关市",
          wechat: "wjw13076271026",
          sex: 0,
          birthday: new Date(1999, 4, 18),
          status: "广东海洋大学 2021届 软件工程 本科生",
          sort: 0,
        },
        edus: {
          title: "教育经历",
          children: [
            {
              school: "广东海洋大学",
              major: "软件工程",
              degree: "本科",
              faculty: "数学与计算机学院",
              city: "广东湛江",
              timerange: ["2017/09", "2021/06"],
              beginTime: "2017/09",
              endTime: "2021/06",
              experiences: `<ul>
<li>
<p>在校GPA3.5+，专业前10%，已过英语四级</p>
</li>
<li>
<p>参加大学生创新创业大赛获省级立项，有论文一篇（已被EI, Scopus检索）</p>
</li>
<li>
<p>广东海洋大学技术团队创忆团队成员，作为主力人员参与多个项目的研发</p>
</li>
<li>
<p>修读数据结构与算法，数据库原理及应用，计算机组成与结构，web开发技术，计算机网络，操作系统，软件工程等课程</p>
</li>
</ul>`,
            },
            {
              school: "广东海洋大学",
              major: "软件工程",
              degree: "硕士",
              faculty: "数学与计算机学院",
              city: "广东湛江",
              timerange: ["2017/09", "2021/06"],
              beginTime: "2017/09",
              endTime: "2021/06",
              experiences: `<ul>
            <li>
            <p>在校GPA4.0+，专业前10%，已过英语四级</p>
            </li>
            <li>
            <p>参加大学生创新创业大赛获省级立项，有论文一篇（已被EI, Scopus检索）</p>
            </li>
            <li>
            <p>广东海洋大学技术团队创忆团队成员，作为主力人员参与多个项目的研发</p>
            </li>
            <li>
            <p>修读数据结构与算法，数据库原理及应用，计算机组成与结构，web开发技术，计算机网络，操作系统，软件工程等课程</p>
            </li>
            </ul>`,
            },
          ],
          sort: 1,
        },
        skills: {
          title: "专业技能",
          desc:
            "<ul>\n<li>\n<p>熟悉Java面向对象，集合和多线程相关等基础知识</p>\n</li>\n<li>\n<p>熟悉MySQL数据库，MySQL事务、索引和SQL调优</p>\n</li>\n<li>\n<p>熟悉Spring、SpringMVC、SpringBoot开源框架，了解SpringCloud微服务</p>\n</li>\n<li>\n<p>熟悉Redis和RabbitMQ等中间件</p>\n</li>\n<li>\n<p>熟悉Linux常用命令，熟练使用Git工具</p>\n</li>\n<li>\n<p>掌握HTML、CSS和JS，了解Python和神经网络</p>\n</li>\n</ul>",
          sort: 3,
        },
        internships: {
          title: "实习经历",
          children: [
            {
              companyName: "深圳云之家网络有限公司",
              position: "Java后台开发实习生",
              department: "产品与项目交付部",
              city: "广东深圳",
              timerange: ["2020/08", "2020/11"],
              beginTime: "2020/08",
              endTime: "2020/11",
              desc:
                "<ul>\n<li>职责：目前在深圳云之家网络有限公司（金蝶）实习，参与云之家生态圈项目，和部门另一位同事负责生态圈接入金蝶苍穹系统，企业官网定制化项目。目前正在实习和学习中。</li>\n<li>成果：获得云之家9月月度激励奖</li>\n</ul>",
            },
            // {
            //   companyName: "深圳云之家网络有限公司",
            //   position: "Java后台开发实习生",
            //   department: "产品与项目交付部",
            //   city: "广东深圳",
            //   timerange: ["2020/08", "2020/11"],
            //   beginTime: "2020/08",
            //   endTime: "2020/11",
            //   desc:
            //     "<ul>\n<li>职责：目前在深圳云之家网络有限公司（金蝶）实习，参与云之家生态圈项目，和部门另一位同事负责生态圈接入金蝶苍穹系统，企业官网定制化项目。目前正在实习和学习中。</li>\n<li>成果：获得云之家9月月度激励奖</li>\n</ul>",
            // },
          ],
          sort: 2,
        },
        projects: {
          title: "开源项目和作品",
          children: [
            {
              projectName: "基于深度学习的养殖水质分析决策系统",
              duty: "Java后台开发实习生",
              city: "广东深圳",
              timerange: ["2020/08", "2020/11"],
              beginTime: "2020/08",
              endTime: "2020/11",
              desc:
                "<ul>\n<li>职责：目前在深圳云之家网络有限公司（金蝶）实习，参与云之家生态圈项目，和部门另一位同事负责生态圈接入金蝶苍穹系统，企业官网定制化项目。目前正在实习和学习中。</li>\n<li>成果：获得云之家9月月度激励奖</li>\n</ul>",
            },
          ],
          sort: 2,
        },
        summaries: {
          title: "个人总结",
          desc:
            "<ul>\n<li>\n<p>对待工作认真负责，时刻保持学习热情，坚持维护个人技术博客</p>\n</li>\n<li>\n<p>熟悉MySQL数据库，MySQL事务、索引和SQL调优</p>\n</li>\n<li>\n<p>熟悉Spring、SpringMVC、SpringBoot开源框架，了解SpringCloud微服务</p>\n</li>\n<li>\n<p>熟悉Redis和RabbitMQ等中间件</p>\n</li>\n<li>\n<p>熟悉Linux常用命令，熟练使用Git工具</p>\n</li>\n<li>\n<p>掌握HTML、CSS和JS，了解Python和神经网络</p>\n</li>\n</ul>",
          sort: 3,
        },
        communities: {
          title: "社团和组织经历",
          children: [
            {
              activityName: "国家资助下乡行",
              duty: "Java后台开发实习生",
              city: "广东深圳",
              timerange: ["2020/08", "2020/11"],
              beginTime: "2020/08",
              endTime: "2020/11",
              desc:
                "<ul>\n<li>职责：目前在深圳云之家网络有限公司（金蝶）实习，参与云之家生态圈项目，和部门另一位同事负责生态圈接入金蝶苍穹系统，企业官网定制化项目。目前正在实习和学习中。</li>\n<li>成果：获得云之家9月月度激励奖</li>\n</ul>",
            },
          ],
          sort: 2,
        },
      },
      basicInfoEdit: false,
      edusEdit: false,
      skillsEdit: false,
      internshipsEdit: false,
      projectsEdit: false,
      summariesEdit: false,
      communitiesEdit: false,
      address: provinceAndCityData,
      formattedAddress: "",
      sexes: [
        {
          value: 0,
          label: "男",
        },
        {
          value: 1,
          label: "女",
        },
      ],
      curEduIdx: 0,
      curInternshipIdx: 0,
      curProjectIdx: 0,
      curCommunityIdx: 0,
    };
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    window.vue = this;
  },
  methods: {
    // 打印简历pdf
    downloadPDF() {
      this.fullscreenLoading = true;
      setTimeout(() => {
        this.fullscreenLoading = false;
        this.$nextTick(() => {
          document.body.innerHTML = document.getElementById(
            "printerContainer"
          ).innerHTML;
          window.print();
          window.location.reload();
          //   setTimeout(() => {}, 1000); // 增加1s可以避免网络问题导致照片加载之前print
        });
      }, 2000);
    },
    // 计算年龄
    calAge() {
      return calYearSub(this.resumeData.basicInfo.birthday, new Date());
    },
    // 显示对应模块编辑框
    displayItem(itemTag, idx) {
      this.edit = true;
      // 隐藏其他框
      this.basicInfoEdit = false;
      this.edusEdit = false;
      this.skillsEdit = false;
      this.internshipsEdit = false;
      this.projectsEdit = false;
      this.summariesEdit = false;
      this.communitiesEdit = false;
      // 显示当前框
      if (itemTag === "basicInfo") {
        if (this.resumeData.basicInfo.city)
          this.formattedAddress = strToAdd(this.resumeData.basicInfo.city, 2);
        this.basicInfoEdit = true;
      } else if (itemTag === "skills") this.skillsEdit = true;
      else if (itemTag === "summaries") this.summariesEdit = true;
      else if (itemTag === "edus") {
        this.edusEdit = true;
        this.curEduIdx = idx;
      } else if (itemTag === "internships") {
        this.internshipsEdit = true;
        this.curInternshipIdx = idx;
      } else if (itemTag === "projects") {
        this.projectsEdit = true;
        this.curProjectIdx = idx;
      } else if (itemTag === "communities") {
        this.communitiesEdit = true;
        this.curCommunityIdx = idx;
      }
    },
    // 添加节点
    addNode(itemTag) {
      let index = 0;
      if (itemTag === "edus") {
        this.resumeData.edus.children.push({
          timerange: [
            new Date().format("yyyy/MM"),
            new Date().format("yyyy/MM"),
          ],
        });
        index = this.resumeData.edus.children.length - 1;
      } else if (itemTag === "internships") {
        this.resumeData.internships.children.push({
          timerange: [
            new Date().format("yyyy/MM"),
            new Date().format("yyyy/MM"),
          ],
        });
        index = this.resumeData.internships.children.length - 1;
      } else if (itemTag === "projects") {
        this.resumeData.projects.children.push({
          timerange: [
            new Date().format("yyyy/MM"),
            new Date().format("yyyy/MM"),
          ],
        });
        index = this.resumeData.projects.children.length - 1;
      } else if (itemTag === "communities") {
        this.resumeData.communities.children.push({
          timerange: [
            new Date().format("yyyy/MM"),
            new Date().format("yyyy/MM"),
          ],
        });
        index = this.resumeData.communities.children.length - 1;
      }
      this.displayItem(itemTag, index);
    },
    // 保存编辑
    saveItem(itemTag) {
      this.edit = false;
      if (itemTag === "basicInfo") this.basicInfoEdit = false;
      else if (itemTag === "edus") this.edusEdit = false;
      else if (itemTag === "skills") this.skillsEdit = false;
      else if (itemTag === "internships") this.internshipsEdit = false;
      else if (itemTag === "projects") this.projectsEdit = false;
      else if (itemTag === "summaries") this.summariesEdit = false;
      else if (itemTag === "communities") this.communitiesEdit = false;
    },
    // 取消编辑
    quitItemEdit(itemTag) {
      this.edit = false;
      if (itemTag === "basicInfo") this.basicInfoEdit = false;
      else if (itemTag === "edus") this.edusEdit = false;
      else if (itemTag === "skills") this.skillsEdit = false;
      else if (itemTag === "internships") this.internshipsEdit = false;
      else if (itemTag === "projects") this.projectsEdit = false;
      else if (itemTag === "summaries") this.summariesEdit = false;
      else if (itemTag === "communities") this.communitiesEdit = false;
    },
    // 地址拼接
    handleChangeAddress(value) {
      this.resumeData.basicInfo.city = addToStr(value);
    },
    // 上传照片成功
    uploadPhotoSuccess(res, file) {
      URL.createObjectURL(file.raw);
      if (res.code != 2000) {
        //操作错误，友好提示
        this.$message({
          type: "error",
          message: "失败! 错误码:" + resp.code,
        });
        return;
      }
      this.resumeData.basicInfo.photo = res.data.fullpath;
    },
    // 图片校验
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是 JPG/PNG 格式!");
      }
      if (!isLt5M) {
        this.$message.error("上传头像图片大小不能超过 5MB!");
      }
      return (isJPG || isPNG) && isLt5M;
    },
  },
};
</script>

<style>
/* A4纸大小 */
.box-card {
  margin: 10px auto;
  width: 210mm;
  /* height: 297mm;   */ /* TODO这个限定了纸张高度，虽然打印会自动多页纸 */
  /* width: 595px;
  height: 842px; */
  /* width: 826px;
  height: 1169px; */
}

/* 编辑状态移到左边 */
.box-card-edit {
  float: left;
  margin: 10px 10px;
  width: 210mm;
  /*height: 297mm; */ /* TODO这个限定了纸张高度，虽然打印会自动多页纸 */
  /* width: 595px;
  height: 842px; */
  /* width: 826px;
  height: 1169px; */
}

/* 编辑框 */
.item-edit-css {
  float: left;
  margin-top: 10px;
  width: 500px;
}

/* 添加item按钮 */
.freework-classic-add-button {
  position: absolute;
  left: 275mm;
  z-index: 99;
}

/* 打印按钮 */
.download-css {
  position: fixed;
  z-index: 99; /*设置优先级显示，保证不会被覆盖*/
  right: 30px;
  bottom: 50px;
}

/* 基本信息和图片的摆放 */
.freework-classic-infotextbox {
  float: left;
  width: 80%;
}

/* 小一寸照片尺寸 */
.freework-classic-photo {
  float: right;
  width: 2.2cm;
  height: 3.2cm;
  margin-top: 2%;
}

/* 名字单独 */
.freework-classic-name {
  font-weight: bold;
  font-size: 6mm;
}

/* title稍大 */
.freework-classic-title {
  font-weight: bold;
  font-size: 5mm;
}

/* 分割线不设置样式不打印 */
.freework-classic-hr {
  /* height: 0px;
  border-top: 2px solid #999;
  border-right: 0px;
  border-bottom: 0px;
  border-left: 0px; */
  margin: 2px;
  border-top: 1px solid;
  background-color: #999;
}

/* 垂直分割线 */
.freework-classic-hr-vert {
  border-left: 1px solid #dcdfe6;
}

/* 学校加粗 */
.freework-classic-school {
  font-weight: bold;
}

/* 上传图片按钮框 */
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 130px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 130px;
  height: 178px;
  display: block;
}

/* 下面三个样式用于子div设置了float情况下父div自动换行，item用于一大板块，single-line用于板块内的细节div */
.freework-classic-item {
  margin-top: 20px;
  overflow: hidden;
}

.freework-classic-item-top {
  overflow: hidden;
}

.freework-classic-single-line {
  /* margin-top: 2px; */
  overflow: hidden;
}

/* 正文字体 */
.freework-classic-text {
  float: left;
  line-height: 23px;
}

/* 板块悬浮变色 */
.freework-classic-hover {
  cursor: pointer;
}

.freework-classic-hover:hover {
  cursor: pointer;
  background-color: #fed;
}

/* 调整富文本编辑器带来的样式 */
.freework-classic-text ul {
  margin: 0px;
}

.freework-classic-text ul p {
  margin: 0px;
}

.freework-classic-text p {
  margin: 0px;
}

@media print {
  .noprint {
    display: none;
  }
}
</style>