import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { MPLS_LSP_PATH_TYPE_OPTIONS, MPLS_LSP_CRITICALITY_OPTIONS } from './types';
import type { MplsLspFilter } from './types';

interface Props {
  value: MplsLspFilter;
  onChange: (next: MplsLspFilter) => void;
  onReset: () => void;
}

export function MplsLspFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: MplsLspFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Mpls Lsp filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Mpls Lsp"
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
      <FilterField label="Lsp Name">
        <Input
          aria-label="Lsp Name filter"
          value={value.lspName ?? ''}
          onChange={(v) => patch({ lspName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Head End Id">
        <Input
          aria-label="Head End Id filter"
          placeholder="UUID"
          value={value.headEndId ?? ''}
          onChange={(v) => patch({ headEndId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tail End Id">
        <Input
          aria-label="Tail End Id filter"
          placeholder="UUID"
          value={value.tailEndId ?? ''}
          onChange={(v) => patch({ tailEndId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Bandwidth Mbps">
        <RangeInputs
          ariaLabel="Bandwidth Mbps"
          min={value.bandwidthMbpsMin}
          max={value.bandwidthMbpsMax}
          onChange={(min, max) => patch({ bandwidthMbpsMin: min, bandwidthMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Setup Priority">
        <RangeInputs
          ariaLabel="Setup Priority"
          min={value.setupPriorityMin}
          max={value.setupPriorityMax}
          onChange={(min, max) => patch({ setupPriorityMin: min, setupPriorityMax: max })}
        />
      </FilterField>
      <FilterField label="Path Type">
        <MultiSelect
          ariaLabel="Path Type filter"
          options={MPLS_LSP_PATH_TYPE_OPTIONS}
          value={value.pathType ?? []}
          onChange={(v) => patch({ pathType: v.length ? v : undefined })}
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
          options={MPLS_LSP_CRITICALITY_OPTIONS}
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
