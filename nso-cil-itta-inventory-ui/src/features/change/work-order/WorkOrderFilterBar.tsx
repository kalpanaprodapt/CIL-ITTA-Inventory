import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { WORK_ORDER_WORK_STATE_OPTIONS, WORK_ORDER_LIFECYCLE_STAGE_OPTIONS, WORK_ORDER_CRITICALITY_OPTIONS } from './types';
import type { WorkOrderFilter } from './types';

interface Props {
  value: WorkOrderFilter;
  onChange: (next: WorkOrderFilter) => void;
  onReset: () => void;
}

export function WorkOrderFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: WorkOrderFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Work Order filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Work Order"
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
      <FilterField label="Work Order Number">
        <Input
          aria-label="Work Order Number filter"
          value={value.workOrderNumber ?? ''}
          onChange={(v) => patch({ workOrderNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Work Type">
        <Input
          aria-label="Work Type filter"
          value={value.workType ?? ''}
          onChange={(v) => patch({ workType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Assigned Technician Id">
        <Input
          aria-label="Assigned Technician Id filter"
          placeholder="UUID"
          value={value.assignedTechnicianId ?? ''}
          onChange={(v) => patch({ assignedTechnicianId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Due Date">
        <DateRange
          ariaLabel="Due Date"
          from={value.dueDateFrom}
          to={value.dueDateTo}
          onChange={(from, to) => patch({ dueDateFrom: from, dueDateTo: to })}
        />
      </FilterField>
      <FilterField label="Work State">
        <MultiSelect
          ariaLabel="Work State filter"
          options={WORK_ORDER_WORK_STATE_OPTIONS}
          value={value.workState ?? []}
          onChange={(v) => patch({ workState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={WORK_ORDER_LIFECYCLE_STAGE_OPTIONS}
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
          options={WORK_ORDER_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
