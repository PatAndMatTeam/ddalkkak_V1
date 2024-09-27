import {lazy, Suspense} from "react";
import {Navigate} from "react-router-dom";

const Loading = <div>Loading....</div>
const BoardList = lazy(()=> import("../pages/board/ListPage"))
const BoardRead = lazy( ()=> import("../pages/board/ReadPage"))
const BoardAdd = lazy( ()=> import("../pages/board/AddPage"))
const BoardModify = lazy(() => import("../pages/board/ModifyPage"))
const AnlyRead = lazy(() => import("../pages/analysis/AnlyReadPage"))


const boardRouter = () => {
    return [
        {
            path : 'list',
            element : <Suspense fallback={Loading}><BoardList/></Suspense>
        },
        {
            path:'',
            element: <Navigate replace={true} to={'list'} />
        },
        {
            path:'read/:tno',
            element:<Suspense fallback={Loading}><BoardRead/></Suspense>
        },
        {
            path:'add',
            element:<Suspense fallback={Loading}><BoardAdd/></Suspense>
        },
        {
            path: "modify/:tno",
            element: <Suspense fallback={Loading}><BoardModify/></Suspense>
        },
        {
            path:'read/anlz/:tno',
            element:<Suspense fallback={Loading}><AnlyRead/></Suspense>
        }
    ]
}
export  default  boardRouter