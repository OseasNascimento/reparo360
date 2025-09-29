import React from 'react';

type Delta = {
  value: number;               // ex: +12.3 ou -8.5
  positiveIsGood?: boolean;    // padrão: true (verde quando delta>0)
  suffix?: string;             // ex: '%', 'R$'
  label?: string;              // ex: 'vs mês anterior'
};

export type CardMetricProps = {
  title: string;
  value: React.ReactNode;
  icon?: React.ReactNode;
  hint?: string;         // texto pequeno abaixo do título
  delta?: Delta;
};

export const CardMetric: React.FC<CardMetricProps> = ({ title, value, icon, hint, delta }) => {
  const deltaNum = delta?.value ?? 0;
  const positiveIsGood = delta?.positiveIsGood ?? true;
  const isPositive = deltaNum >= 0;
  const good = positiveIsGood ? isPositive : !isPositive;
  const color =
    delta ? (good ? 'text-emerald-600 bg-emerald-50' : 'text-rose-600 bg-rose-50') : 'text-slate-500';

  return (
    <div
      className="
        rounded-2xl border border-slate-200/70 bg-white/80 backdrop-blur
        shadow-[0_10px_30px_-12px_rgba(2,6,23,0.15)] hover:shadow-[0_18px_36px_-10px_rgba(2,6,23,0.2)]
        transition-shadow duration-300 p-4 flex items-start gap-4
      "
    >
      <div
        className="
          h-10 w-10 shrink-0 rounded-xl grid place-items-center
          bg-gradient-to-br from-indigo-500/10 to-indigo-500/0 text-indigo-600
        "
      >
        {icon}
      </div>

      <div className="flex-1">
        <div className="text-xs font-medium text-slate-500">
          {title} {hint ? <span className="text-slate-400">• {hint}</span> : null}
        </div>
        <div className="mt-1 text-2xl font-bold tracking-tight text-slate-900">{value}</div>

        {delta && (
          <div className={`mt-2 inline-flex items-center gap-1 rounded-lg px-2 py-1 text-xs ${color}`}>
            <span className="font-semibold">
              {isPositive ? '▲' : '▼'} {Math.abs(deltaNum).toLocaleString('pt-BR')}
              {delta.suffix ?? ''}
            </span>
            {delta.label ? <span className="text-slate-500/70 ml-1">{delta.label}</span> : null}
          </div>
        )}
      </div>
    </div>
  );
};

export default CardMetric;
