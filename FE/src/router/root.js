import { Suspense, lazy } from "react";
const { createBrowserRouter } = require("react-router-dom");

const Loading = <div>Loading....</div>
const Main = lazy(() => import("../pages/MainPage"))
const BoardList = lazy(()=> import("../pages/board/ListPage"))
const BoardRead = lazy(()=> import("../pages/board/ReadPage"))
const BoardAdd = lazy(()=> import("../pages/board/AddPage"))

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
    }
])
export default root;