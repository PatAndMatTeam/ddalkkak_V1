import { Suspense, lazy } from "react";
const { createBrowserRouter } = require("react-router-dom");

const Loading = <div>Loading....</div>
const Main = lazy(() => import("../pages/MainPage"))
const BoardList = lazy(()=> import("../pages/board/ListPage"))
const BoardRead = lazy(()=> import("../pages/board/ReadPage"))
const BoardAdd = lazy(()=> import("../pages/board/AddPage"))
const AnlyAdd = lazy(()=> import("../pages/analysis/AnlyAddPage"))
const AnlyRead = lazy(()=> import("../pages/analysis/AnlyReadPage"))
const Login = lazy(()=> import("../pages/login/LoginPage"))
const LoginSuccess = lazy(()=> import("../pages/login/LoginSuccess"))

const root = createBrowserRouter([
    {
        path: "",
        element: <Suspense fallback={Loading}><Main/></Suspense>
    },
    {
        path: ":category", // 경로 변수 추가
        element : <Suspense fallback={Loading}><BoardList/></Suspense>
    },
    {
        path:':category/read/:tno',
        element:<Suspense fallback={Loading}><BoardRead/></Suspense>
    },
    {
        path:':category/add',
        element:<Suspense fallback={Loading}><BoardAdd/></Suspense>
    },
    {
        path:':category/read/:tno/add',
        element:<Suspense fallback={Loading}><AnlyAdd/></Suspense>
    },
    {
        path:':category/read/:tno/:anlyId',
        element:<Suspense fallback={Loading}><AnlyRead/></Suspense>
    },
    {
        path: "signup", // 경로 변수 추가
        element : <Suspense fallback={Loading}><Login/></Suspense>
    },
    {
        path: "login", // 경로 변수 추가
        element : <Suspense fallback={Loading}><Login/></Suspense>
    },
    {
        path: "login/success", // 경로 변수 추가
        element : <Suspense fallback={Loading}><LoginSuccess/></Suspense>
    }



])
export default root;