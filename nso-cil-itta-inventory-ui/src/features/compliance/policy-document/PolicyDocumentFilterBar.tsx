import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { POLICY_DOCUMENT_LIFECYCLE_STAGE_OPTIONS, POLICY_DOCUMENT_CRITICALITY_OPTIONS } from './types';
import type { PolicyDocumentFilter } from './types';

interface Props {
  value: PolicyDocumentFilter;
  onChange: (next: PolicyDocumentFilter) => void;
  onReset: () => void;
}

export function PolicyDocumentFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PolicyDocumentFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Policy Document filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Policy Document"
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
      <FilterField label="Title">
        <Input
          aria-label="Title filter"
          value={value.title ?? ''}
          onChange={(v) => patch({ title: v || undefined })}
        />
      </FilterField>
      <FilterField label="Doc Version">
        <Input
          aria-label="Doc Version filter"
          value={value.docVersion ?? ''}
          onChange={(v) => patch({ docVersion: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective Date">
        <DateRange
          ariaLabel="Effective Date"
          from={value.effectiveDateFrom}
          to={value.effectiveDateTo}
          onChange={(from, to) => patch({ effectiveDateFrom: from, effectiveDateTo: to })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Document Url">
        <Input
          aria-label="Document Url filter"
          value={value.documentUrl ?? ''}
          onChange={(v) => patch({ documentUrl: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={POLICY_DOCUMENT_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={POLICY_DOCUMENT_CRITICALITY_OPTIONS}
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
