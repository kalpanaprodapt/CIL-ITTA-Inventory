import type { ReactNode } from 'react';
import type { Option } from './form';

export function FilterField({ label, children }: { label: string; children: ReactNode }) {
  return (
    <div className="filter-field">
      <span className="filter-field__label">{label}</span>
      {children}
    </div>
  );
}

export function Input(props: { value: string; onChange: (v: string) => void; placeholder?: string; 'aria-label'?: string }) {
  const { value, onChange, placeholder, ...rest } = props;
  return (
    <input className="input" value={value} placeholder={placeholder} onChange={(ev) => onChange(ev.target.value)} {...rest} />
  );
}

export function MultiSelect<V extends string>(props: {
  options: readonly Option<V>[];
  value: V[];
  onChange: (value: V[]) => void;
  ariaLabel: string;
}) {
  const { options, value, onChange, ariaLabel } = props;
  return (
    <select
      multiple
      className="input input--multi"
      aria-label={ariaLabel}
      value={value}
      onChange={(ev) => onChange(Array.from(ev.target.selectedOptions).map((o) => o.value as V))}
    >
      {options.map((o) => (
        <option key={o.value} value={o.value}>
          {o.label}
        </option>
      ))}
    </select>
  );
}

export function RangeInputs(props: {
  min?: number;
  max?: number;
  ariaLabel: string;
  onChange: (min: number | undefined, max: number | undefined) => void;
}) {
  const { min, max, ariaLabel, onChange } = props;
  const parse = (v: string) => (v === '' ? undefined : Number(v));
  return (
    <div className="range">
      <input
        className="input"
        type="number"
        aria-label={`${ariaLabel} minimum`}
        value={min ?? ''}
        onChange={(ev) => onChange(parse(ev.target.value), max)}
      />
      <input
        className="input"
        type="number"
        aria-label={`${ariaLabel} maximum`}
        value={max ?? ''}
        onChange={(ev) => onChange(min, parse(ev.target.value))}
      />
    </div>
  );
}

export function DateRange(props: {
  from?: string;
  to?: string;
  ariaLabel: string;
  onChange: (from: string | undefined, to: string | undefined) => void;
}) {
  const { from, to, ariaLabel, onChange } = props;
  return (
    <div className="range">
      <input
        className="input"
        type="date"
        aria-label={`${ariaLabel} from`}
        value={from?.slice(0, 10) ?? ''}
        onChange={(ev) => onChange(ev.target.value || undefined, to)}
      />
      <input
        className="input"
        type="date"
        aria-label={`${ariaLabel} to`}
        value={to?.slice(0, 10) ?? ''}
        onChange={(ev) => onChange(from, ev.target.value || undefined)}
      />
    </div>
  );
}

export function TriState(props: { value?: boolean; ariaLabel: string; onChange: (v: boolean | undefined) => void }) {
  const { value, ariaLabel, onChange } = props;
  const text = value === undefined ? '' : String(value);
  return (
    <select
      className="input"
      aria-label={ariaLabel}
      value={text}
      onChange={(ev) => onChange(ev.target.value === '' ? undefined : ev.target.value === 'true')}
    >
      <option value="">Any</option>
      <option value="true">Yes</option>
      <option value="false">No</option>
    </select>
  );
}
