webpackJsonp([2],{"3WME":function(t,e){},DRYX:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container"},[i("el-steps",{attrs:{active:4,simple:""}},[i("el-step",{attrs:{title:"Create",icon:"el-icon-circle-plus-outline"}}),t._v(" "),i("el-step",{attrs:{title:"Server",icon:"el-icon-upload"}}),t._v(" "),i("el-step",{attrs:{title:"Jenkinsfile",icon:"el-icon-edit"}}),t._v(" "),i("el-step",{attrs:{title:"Build",icon:"el-icon-lightning"}}),t._v(" "),i("el-step",{attrs:{title:"Result",icon:"el-icon-s-promotion"}})],1),t._v(" "),i("h1",{staticStyle:{"font-family":"Microsoft YaHei","font-size":"25px"}},[t._v("Build Job")]),t._v(" "),i("el-button",{staticClass:"button",attrs:{type:"primary",plain:""},on:{click:t.startBuild}},[t._v("Get Building Result")]),t._v(" "),i("a",{attrs:{href:"http://3.15.149.72:8080/job/"+t.$store.state.projectName}},[t._v("Link To Jenkins Result")]),t._v(" "),i("el-input",{attrs:{type:"textarea",rows:32,placeholder:"ConsoleOutput:"},model:{value:t.textarea,callback:function(e){t.textarea=e},expression:"textarea"}})],1)},staticRenderFns:[]};var a=i("VU/8")({data:function(){return{textarea:""}},methods:{startBuild:function(){var t=this,e=this;this.loading=!0,this.postRequest("/jenkins/output",{projectName:this.$store.state.projectName}).then(function(i){e.loading=!1,i&&200==i.status&&(t.textarea=i.data)})}}},s,!1,function(t){i("d9ug")},null,null);e.default=a.exports},DhZR:function(t,e){},Fl6l:function(t,e){},RJDG:function(t,e,i){var s={"./PerBuild.vue":"kVG2","./PerCreate.vue":"gQW8","./PerJkfile.vue":"lgBs","./PerResult.vue":"DRYX","./PerServer.vue":"vIsk"};function a(t){return i(n(t))}function n(t){var e=s[t];if(!(e+1))throw new Error("Cannot find module '"+t+"'.");return e}a.keys=function(){return Object.keys(s)},a.resolve=n,t.exports=a,a.id="RJDG"},ZUxo:function(t,e){},d9ug:function(t,e){},gQW8:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=i("mvHQ"),a=i.n(s),n={data:function(){return{repoApi:[],value:"",search:""}},computed:{searchData:function(){var t=this,e=this.search;return console.log("search = "+e),e?this.repoApi.filter(function(e){return e.name.toLowerCase().startsWith(t.search.toLowerCase())}):this.repoApi},user:function(){return this.$store.state.user}},mounted:function(){this.initData()},filters:{formatDate:function(t){var e=new Date(t),i=e.getFullYear(),s=e.getMonth()+1;s=s<10?"0"+s:s;var a=e.getDate();a=a<10?"0"+a:a;var n=e.getHours();n=n<10?"0"+n:n;var r=e.getMinutes();r=r<10?"0"+r:r;var o=e.getSeconds();return i+"-"+s+"-"+a+" "+n+":"+r+":"+(o=o<10?"0"+o:o)}},methods:{initData:function(){var t=this;this.getRequest("https://api.github.com/users/"+this.user.githubName+"/repos").then(function(e){if(e&&200==e.status){var i=e.data,s={Java:"#b07219",JavaScript:"#f1e05a",HTML:"#e34c26",Swift:"#ffac45",Python:"#3572A5"};for(var a in i.sort(function(t,e){return new Date(e.updated_at).getTime()-new Date(t.updated_at).getTime()}),i)i[a].color=s[i[a].language];t.repoApi=i,console.log(i)}})},clearSearch:function(){this.search=""},createJenkinsJob:function(t,e){var i=(new Date).getTime()/1e3;console.log(t,e),this.$store.commit("setRepoName",t),this.$store.commit("setProjectName",t+i),this.$store.commit("setLanguage",e),window.localStorage.setItem("repoName",a()(t)),window.localStorage.setItem("projectName",a()(t+i)),window.localStorage.setItem("language",a()(e));this.loading=!0,this.$router.push({path:"/config/server"})}}},r={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container"},[i("el-steps",{staticClass:"process",attrs:{active:0,simple:""}},[i("el-step",{attrs:{title:"Create",icon:"el-icon-circle-plus-outline"}}),t._v(" "),i("el-step",{attrs:{title:"Server",icon:"el-icon-upload"}}),t._v(" "),i("el-step",{attrs:{title:"Jenkinsfile",icon:"el-icon-edit"}})],1),t._v(" "),i("div",{staticClass:"new-pipeline"},[t._v("New Pipeline")]),t._v(" "),i("div",{staticClass:"title"},[t._v("Select a repository")]),t._v(" "),i("br"),t._v(" "),i("div",{staticClass:"repo-filter"},[i("i",{staticClass:"el-icon-search"}),t._v(" "),i("input",{directives:[{name:"model",rawName:"v-model",value:t.search,expression:"search"}],staticClass:"repo-filter-search-input",attrs:{placeholder:"filter by keywords"},domProps:{value:t.search},on:{input:function(e){e.target.composing||(t.search=e.target.value)}}}),t._v(" "),i("i",{staticClass:"el-icon-close",on:{click:function(e){return t.clearSearch()}}})]),t._v(" "),t.searchData.length>0?i("div",{staticStyle:{width:"70%"}},t._l(t.searchData,function(e){return i("div",{staticClass:"repo-list",on:{click:function(i){return t.createJenkinsJob(e.name,e.language)}}},[i("img",{staticClass:"repo-img",attrs:{src:e.owner.avatar_url}}),t._v(" "),i("div",{staticStyle:{float:"left",margin:"8px 0 8px 0"}},[i("div",[i("span",{staticClass:"repo-name"},[t._v(t._s(e.full_name))]),t._v(" "),e.fork?i("span",{staticClass:"repo-fork"},[t._v("fork")]):t._e()]),t._v(" "),i("span",{staticClass:"repo-time",attrs:{height:"auto"}},[t._v(t._s(t._f("formatDate")(e.updated_at)))])]),t._v(" "),i("div",{staticClass:"repo-language"},[i("span",{staticClass:"repo-language-color",style:{background:e.color}}),t._v(" "),i("span",[t._v(t._s(e.language))])])])}),0):i("div",{staticStyle:{width:"70%"}},[i("i",{staticClass:"el-icon-coffee-cup"}),t._v(" "),i("span",[t._v("No matching repositories were found. If you can't find a repository, make sure you provide access.You may also select a specific connection.")])])],1)},staticRenderFns:[]};var o=i("VU/8")(n,r,!1,function(t){i("ZUxo")},null,null);e.default=o.exports},kVG2:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container"},[i("el-steps",{attrs:{active:3,simple:""}},[i("el-step",{attrs:{title:"Create",icon:"el-icon-circle-plus-outline"}}),t._v(" "),i("el-step",{attrs:{title:"Server",icon:"el-icon-upload"}}),t._v(" "),i("el-step",{attrs:{title:"Jenkinsfile",icon:"el-icon-edit"}}),t._v(" "),i("el-step",{attrs:{title:"Build",icon:"el-icon-lightning"}}),t._v(" "),i("el-step",{attrs:{title:"Result",icon:"el-icon-s-promotion"}})],1),t._v(" "),i("h1",{staticClass:"title"},[t._v("Build Job")]),t._v(" "),i("el-button",{staticClass:"button",attrs:{type:"primary",plain:""},on:{click:t.startBuild}},[t._v("Start Building")])],1)},staticRenderFns:[]};var a=i("VU/8")({data:function(){return{textarea:""}},methods:{startBuild:function(){var t=this,e=this;this.loading=!0,this.postRequest("/jenkins/build",{jobName:this.$store.state.projectName}).then(function(i){e.loading=!1,i&&200==i.status&&t.$router.push({path:"/output"})})}}},s,!1,function(t){i("Fl6l")},null,null);e.default=a.exports},lgBs:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=i("Lakc"),a={data:function(){return{codes:""}},methods:{initData:function(){var t=this;this.postRequest("/github/content",{repo:JSON.parse(window.localStorage.getItem("repoName")),language:JSON.parse(window.localStorage.getItem("language")),githubName:this.user.githubName,githubToken:this.user.githubToken}).then(function(e){e&&200==e.status&&(t.codes=e.data,t.initEditor())})},initEditor:function(){this.monacoEditor=s.a.create(this.$refs.container,{value:this.codes,language:"javascript",theme:"vs",fontSize:14,automaticLayout:!0,autoIndent:!0,editorOptions:this.editorOptions})},submitJenkinsfile:function(){var t=this,e=this;this.loading=!0;var i=JSON.parse(window.localStorage.getItem("jenkinsId"));this.postRequest("/project/update",{name:JSON.parse(window.localStorage.getItem("projectName")),type:0,enable:!0}).then(function(t){}),this.postRequest("/github/commit",{codeContent:this.monacoEditor.getValue(),repo:JSON.parse(window.localStorage.getItem("repoName")),githubName:this.user.githubName,githubToken:this.user.githubToken}).then(function(s){e.loading=!1,s&&200==s.status&&(t.loading=!0,t.postRequest("/jenkins/build",{jobName:JSON.parse(window.localStorage.getItem("projectName")),type:i}).then(function(t){e.loading=!1,t&&200==t.status&&(1==i||2==i?window.open("http://3.15.149.72:8080/job/"+JSON.parse(window.localStorage.getItem("projectName"))):3==i&&window.open("http://13.125.214.112:30002/job/"+JSON.parse(window.localStorage.getItem("projectName"))))}))})}},mounted:function(){this.initData()},computed:{user:function(){return this.$store.state.user}}},n={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container"},[i("el-steps",{staticClass:"process",attrs:{active:2,simple:""}},[i("el-step",{attrs:{title:"Create",icon:"el-icon-circle-plus-outline"}}),t._v(" "),i("el-step",{attrs:{title:"Server",icon:"el-icon-upload"}}),t._v(" "),i("el-step",{attrs:{title:"Jenkinsfile",icon:"el-icon-edit"}})],1),t._v(" "),i("div",{staticStyle:{display:"inline-block",width:"98%","text-align":"left"}},[t._m(0),t._v(" "),i("button",{staticClass:"fl-right run-button",on:{click:t.submitJenkinsfile}},[t._v("Save and run")])]),t._v(" "),i("div",{staticClass:"monaco-container"},[i("div",{staticClass:"file-name"},[t._v("JenkinsFile")]),t._v(" "),i("div",{ref:"container",staticClass:"monaco-editor"})])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"fl-left"},[e("div",{staticClass:"new-pipeline"},[this._v("New Pipeline")]),this._v(" "),e("div",{staticClass:"title"},[this._v("Review your jenkins file")])])}]};var r=i("VU/8")(a,n,!1,function(t){i("DhZR")},null,null);e.default=r.exports},vIsk:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=i("mvHQ"),a=i.n(s),n=i("Lakc"),r={data:function(){return{formLabelAlign:{serverId:"",bashContent:""},serverList:[{serverName:"Windows Server",serverId:1},{serverName:"Linux Server",serverId:2},{serverName:"Docker",serverId:3}],codes:"",readOnly:!1,editorOptions:{selectOnLineNumbers:!0,roundedSelection:!1,readOnly:this.readOnly,cursorStyle:"line",automaticLayout:!1,glyphMargin:!0,useTabStops:!1,fontSize:28,autoIndent:!1}}},methods:{initEditor:function(){this.monacoEditor=n.a.create(this.$refs.container,{value:this.codes,language:"javascript",theme:"vs",fontSize:14,automaticLayout:!0,autoIndent:!0,editorOptions:this.editorOptions})},submitBashToServer:function(){var t=this,e=this;this.loading=!0;var i=this.formLabelAlign.serverId;window.localStorage.setItem("jenkinsId",a()(i));var s=JSON.parse(window.localStorage.getItem("projectName")),n=JSON.parse(window.localStorage.getItem("repoName")),r=this.user.githubName,o=JSON.parse(window.localStorage.getItem("language"));console.log("type = "+i),this.postRequest("/jenkins/create",{projectName:s,description:"",repo:n,githubName:r,type:i}).then(function(a){e.loading=!1,a&&200==a.status&&t.postRequest("/project/add",{name:s,author:t.user.name,language:o,type:i}).then(function(s){s&&200==s.status&&3===i?t.$router.push({path:"/config/jkfile"}):t.postRequest("/server/run",{serverId:t.formLabelAlign.serverId,bashContent:t.monacoEditor.getValue()}).then(function(i){if(e.loading=!1,i&&200==i.status){var s=i.data;alert(s),t.$router.push({path:"/config/jkfile"}),t.$message("Submit Successfully!")}})})})}},mounted:function(){this.initEditor()},computed:{user:function(){return this.$store.state.user}}},o={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container"},[i("el-steps",{staticClass:"process",attrs:{active:1,simple:""}},[i("el-step",{attrs:{title:"Create",icon:"el-icon-circle-plus-outline"}}),t._v(" "),i("el-step",{attrs:{title:"Server",icon:"el-icon-upload"}}),t._v(" "),i("el-step",{attrs:{title:"Jenkinsfile",icon:"el-icon-edit"}})],1),t._v(" "),i("div",{staticStyle:{display:"inline-block",width:"98%","text-align":"left"}},[i("div",{staticClass:"fl-left"},[i("div",{staticClass:"new-pipeline"},[t._v("New Pipeline")]),t._v(" "),i("div",{staticStyle:{display:"flex"}},[i("div",{staticClass:"title"},[t._v("Select your environment")]),t._v(" "),i("el-form",{attrs:{"label-width":"20px",model:t.formLabelAlign}},[i("el-form-item",[i("el-select",{staticClass:"form",attrs:{placeholder:"Select environment"},model:{value:t.formLabelAlign.serverId,callback:function(e){t.$set(t.formLabelAlign,"serverId",e)},expression:"formLabelAlign.serverId"}},t._l(t.serverList,function(t,e){return i("el-option",{key:e,attrs:{label:t.serverName,value:t.serverId}})}),1)],1)],1)],1)]),t._v(" "),i("button",{staticClass:"fl-right run-button",on:{click:t.submitBashToServer}},[t._v("Save and run")])]),t._v(" "),i("div",{staticClass:"monaco-container"},[i("div",{staticClass:"file-name"},[t._v("Please Input Bash Script")]),t._v(" "),i("div",{ref:"container",staticClass:"monaco-editor"})])],1)},staticRenderFns:[]};var l=i("VU/8")(r,o,!1,function(t){i("3WME")},null,null);e.default=l.exports}});
//# sourceMappingURL=2.7d7cc7a295f6e4719bc5.js.map