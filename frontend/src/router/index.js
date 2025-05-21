import layoutAdmin from "../layout/admin/Layout";
import layoutLogin from "../layout/user/loginlayout/login";

//admin
import userAdmin from '../pages/admin/user'

//public
import login from "../pages/public/login";
import forgotPage from '../pages/public/forgot'
import datLaiMatKhauPage from '../pages/public/datlaimatkhau'

//user
import taikhoan from "../pages/user/taikhoan";


const publicRoutes = [
    {path: "/", component: login, layout: layoutLogin},
    {path: "/login", component: login, layout: layoutLogin},
    { path: "/forgot", component: forgotPage, layout: layoutLogin },
    { path: "/datlaimatkhau", component: datLaiMatKhauPage, layout: layoutLogin },
];

const userRoutes = [
    {path: "/tai-khoan", component: taikhoan},
];


const adminRoutes = [
    { path: "/admin/user", component: userAdmin, layout: layoutAdmin },
];





export {publicRoutes, adminRoutes, userRoutes};
