import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SERVICE_ORDER_ORDER_TYPE_OPTIONS, SERVICE_ORDER_CRITICALITY_OPTIONS } from './types';
import type { ServiceOrderFilter } from './types';

interface Props {
  value: ServiceOrderFilter;
  onChange: (next: ServiceOrderFilter) => void;
  onReset: () => void;
}

export function ServiceOrderFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ServiceOrderFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Service Order filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Service Order"
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
      <FilterField label="Customer Id">
        <Input
          aria-label="Customer Id filter"
          placeholder="UUID"
          value={value.customerId ?? ''}
          onChange={(v) => patch({ customerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Order Type">
        <MultiSelect
          ariaLabel="Order Type filter"
          options={SERVICE_ORDER_ORDER_TYPE_OPTIONS}
          value={value.orderType ?? []}
          onChange={(v) => patch({ orderType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Requested On">
        <DateRange
          ariaLabel="Requested On"
          from={value.requestedOnFrom}
          to={value.requestedOnTo}
          onChange={(from, to) => patch({ requestedOnFrom: from, requestedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Target Date">
        <DateRange
          ariaLabel="Target Date"
          from={value.targetDateFrom}
          to={value.targetDateTo}
          onChange={(from, to) => patch({ targetDateFrom: from, targetDateTo: to })}
        />
      </FilterField>
      <FilterField label="Priority">
        <RangeInputs
          ariaLabel="Priority"
          min={value.priorityMin}
          max={value.priorityMax}
          onChange={(min, max) => patch({ priorityMin: min, priorityMax: max })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SERVICE_ORDER_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
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
