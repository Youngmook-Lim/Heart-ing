import React, { useEffect, useRef, useState } from "react"

function useDetectClose(initialState:boolean) {
  const ref = useRef<HTMLDivElement | null>(null)
  const [isOpen, setIsOpen] = useState(initialState)

  const removeHandler = (e:MouseEvent) => {
    setIsOpen(!isOpen)
  }

  useEffect(() => {
    const onNotiClick = (e: MouseEvent) => {
      if (ref.current !== null && !ref.current.contains(e.target as Node)) {
        setIsOpen(!isOpen)
      }
    }
    if (isOpen) {
      window.addEventListener("mousedown", onNotiClick)
    }
    return () => {
      window.removeEventListener("mousedown", onNotiClick)
    }
  }, [isOpen])

  return [isOpen, ref, removeHandler]
}

export default useDetectClose