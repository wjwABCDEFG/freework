// export function getIndustryAndPosition() {
//     this.$http.get(`http://localhost:9000/job/company/static`, {
//         params: {
//             fileName: "industry.json"
//         }
//     }).then((resp) => {
//         if (resp.data.code != 2000) {
//             //操作错误，友好提示
//             this.$message({
//                 type: 'error',
//                 message: '获取行业岗位数据失败! 错误码:' + resp.data.code
//             });
//             return ;
//         }
//         return JSON.parse(resp.data.data)
//     });
// }