import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';

import type { PseudoWireFilter } from './types';

interface Props {
  value: PseudoWireFilter;
  onChange: (next: PseudoWireFilter) => void;
  onReset: () => void;
}

export function PseudoWireFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PseudoWireFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Pseudo Wire filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Pseudo Wire"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vc Id">
        <RangeInputs
          ariaLabel="Vc Id"
          min={value.vcIdMin}
          max={value.vcIdMax}
          onChange={(min, max) => patch({ vcIdMin: min, vcIdMax: max })}
        />
      </FilterField>
      <FilterField label="A End Id">
        <Input
          aria-label="A End Id filter"
          placeholder="UUID"
          value={value.aEndId ?? ''}
          onChange={(v) => patch({ aEndId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z End Id">
        <Input
          aria-label="Z End Id filter"
          placeholder="UUID"
          value={value.zEndId ?? ''}
          onChange={(v) => patch({ zEndId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Encapsulation">
        <Input
          aria-label="Encapsulation filter"
          value={value.encapsulation ?? ''}
          onChange={(v) => patch({ encapsulation: v || undefined })}
        />
      </FilterField>
      <FilterField label="Control Word">
        <TriState
          ariaLabel="Control Word filter"
          value={value.controlWord}
          onChange={(v) => patch({ controlWord: v })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
