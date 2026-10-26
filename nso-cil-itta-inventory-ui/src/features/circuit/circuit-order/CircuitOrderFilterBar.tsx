import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CIRCUIT_ORDER_ORDER_STATUS_OPTIONS, CIRCUIT_ORDER_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CircuitOrderFilter } from './types';

interface Props {
  value: CircuitOrderFilter;
  onChange: (next: CircuitOrderFilter) => void;
  onReset: () => void;
}

export function CircuitOrderFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CircuitOrderFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Circuit Order filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Circuit Order"
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
      <FilterField label="Circuit Id">
        <Input
          aria-label="Circuit Id filter"
          placeholder="UUID"
          value={value.circuitId ?? ''}
          onChange={(v) => patch({ circuitId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Order Number">
        <Input
          aria-label="Order Number filter"
          value={value.orderNumber ?? ''}
          onChange={(v) => patch({ orderNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ordered On">
        <DateRange
          ariaLabel="Ordered On"
          from={value.orderedOnFrom}
          to={value.orderedOnTo}
          onChange={(from, to) => patch({ orderedOnFrom: from, orderedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Requested Date">
        <DateRange
          ariaLabel="Requested Date"
          from={value.requestedDateFrom}
          to={value.requestedDateTo}
          onChange={(from, to) => patch({ requestedDateFrom: from, requestedDateTo: to })}
        />
      </FilterField>
      <FilterField label="Committed Date">
        <DateRange
          ariaLabel="Committed Date"
          from={value.committedDateFrom}
          to={value.committedDateTo}
          onChange={(from, to) => patch({ committedDateFrom: from, committedDateTo: to })}
        />
      </FilterField>
      <FilterField label="Order Status">
        <MultiSelect
          ariaLabel="Order Status filter"
          options={CIRCUIT_ORDER_ORDER_STATUS_OPTIONS}
          value={value.orderStatus ?? []}
          onChange={(v) => patch({ orderStatus: v.length ? v : undefined })}
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
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={CIRCUIT_ORDER_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
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
