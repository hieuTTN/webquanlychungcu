import layoutAdmin from "../layout/admin/Layout";
import layoutLogin from "../layout/user/loginlayout/login";

//admin
import userAdmin from '../pages/admin/user'
import AdminBlog from '../pages/admin/blog'
import AdminAddBlog from '../pages/admin/addblog'
import AdminCanHo from '../pages/admin/canho'
import AdminAddCanHo from '../pages/admin/addcanho'

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
    { path: "/admin/blog", component: AdminBlog, layout: layoutAdmin },
    { path: "/admin/add-blog", component: AdminAddBlog, layout: layoutAdmin },
    { path: "/admin/canho", component: AdminCanHo, layout: layoutAdmin },
    { path: "/admin/add-canho", component: AdminAddCanHo, layout: layoutAdmin },
];





export {publicRoutes, adminRoutes, userRoutes};
